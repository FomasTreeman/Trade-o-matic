package dev.fomastree.strategies;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dev.fomastree.backend.Bar;

public class Bollinger implements Strategy {
    private static final BigDecimal UPPER_BAND_SD = BigDecimal.valueOf(2.0);
    private static final BigDecimal UPPER_MIDDLE_BAND_SD = BigDecimal.valueOf(1.0);
    private static final BigDecimal LOWER_MIDDLE_BAND_SD = BigDecimal.valueOf(-1.0);
    private static final BigDecimal LOWER_BAND_SD = BigDecimal.valueOf(-2.0);

    private final int period;
    private final BollingerBandsCalculator calculator;

    public Bollinger(int period) {
        if (period <= 0) {
            throw new IllegalArgumentException("Period must be a positive integer");
        }
        this.period = period;
        this.calculator = new BollingerBandsCalculator();
    }

    private class BollingerBandsCalculator {
        public BollingerBands calculate(List<Bar> prices) {
            if (prices.size() < period) {
                throw new IllegalArgumentException("Not enough data points for the specified period: " + period);
            }

            List<Bar> relevantPrices = prices.subList(prices.size() - period, prices.size());
            BigDecimal sma = calculateSMA(relevantPrices);
            BigDecimal standardDeviation = calculateStandardDeviation(relevantPrices, sma);

            return new BollingerBands(sma, standardDeviation);
        }

        private BigDecimal calculateSMA(List<Bar> prices) {
            return prices.stream()
                    .map(Bollinger::getTypicalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(period), RoundingMode.HALF_UP);
        }

        private BigDecimal calculateStandardDeviation(List<Bar> prices, BigDecimal mean) {
            BigDecimal variance = prices.stream()
                    .map(price -> getTypicalPrice(price).subtract(mean).pow(2))
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(period), RoundingMode.HALF_UP);
            return variance.sqrt(new java.math.MathContext(10));
        }
    }
    
    private static BigDecimal getTypicalPrice(Bar bar) {
        return BigDecimal.valueOf(bar.getClosingPrice())
                .add(BigDecimal.valueOf(bar.getHighPrice()))
                .add(BigDecimal.valueOf(bar.getLowPrice()))
                .divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP);
    }

    private static class BollingerBands {
        private final BigDecimal middleBand;
        private final BigDecimal standardDeviation;

        public BollingerBands(BigDecimal middleBand, BigDecimal standardDeviation) {
            this.middleBand = middleBand;
            this.standardDeviation = standardDeviation;
        }

        public BigDecimal getBand(BigDecimal standardDeviations) {
            return middleBand.add(standardDeviations.multiply(standardDeviation));
        }
    }

    public ArrayList<Bar> JsonArrayToArrayList(JsonArray array) {
        try {

            ArrayList<Bar> barList = new ArrayList<>();
            for (JsonElement element : array) {
                JsonObject tradeJson = element.getAsJsonObject();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

                // Extract data from JSON object
                Double closingPrice = tradeJson.get("c").getAsDouble();
                Double highPrice = tradeJson.get("h").getAsDouble();
                Double lowPrice = tradeJson.get("l").getAsDouble();
                int numberOfTrades = tradeJson.get("n").getAsInt();
                Double openPrice = tradeJson.get("o").getAsDouble();
                Date timestamp = dateFormat.parse(tradeJson.get("t").getAsString());
                Long volume = tradeJson.get("v").getAsLong();
                Double volumeWeightedAveragePrice = tradeJson.get("vw").getAsDouble();

                // Create and add Bar object directly to the list
                barList.add(new Bar(closingPrice, highPrice, lowPrice, numberOfTrades, openPrice, timestamp, volume,
                        volumeWeightedAveragePrice));
            }
            return barList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<Bar>();
    }

    @Override
    public void backTest(JsonArray array, String symbol) {
        ArrayList<Bar> barList = JsonArrayToArrayList(array);
        System.out.println("Running Bollinger strategy on historical data for " + symbol);

        // Execute strategy on the list of bars starting from the minimum (20) days to the recent day
        barList.stream().skip(20).forEach(bar -> {
            List<Bar> prices = barList.subList(0, barList.indexOf(bar) + 1);
            execute(prices);
        });

    }

    @Override
    public Signal execute(List<Bar> prices) {
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("Prices list cannot be null or empty");
        }

        Bar currentBar = prices.get(prices.size() - 1);
        BigDecimal currentPrice = getTypicalPrice(currentBar);
        BollingerBands bands = calculator.calculate(prices);

        if (currentPrice.compareTo(bands.getBand(UPPER_MIDDLE_BAND_SD)) > 0 &&
                currentPrice.compareTo(bands.getBand(UPPER_BAND_SD)) <= 0) {
            System.out.println("SELL");
            return Signal.SELL;
        } else if (currentPrice.compareTo(bands.getBand(LOWER_BAND_SD)) >= 0 &&
                currentPrice.compareTo(bands.getBand(LOWER_MIDDLE_BAND_SD)) < 0) {
            System.out.println("BUY");
            return Signal.BUY;
        } else {
            System.out.println("HOLD");
            return Signal.HOLD;
        }
    }
}