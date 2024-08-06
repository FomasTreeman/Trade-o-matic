package dev.fomastree;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dev.fomastree.backend.Bar;
import dev.fomastree.backend.JSON;
import dev.fomastree.strategies.Strategy;
import dev.fomastree.strategies.StrategyFactory;

public class App {
    public static void main(String[] args) {
        final String symbol = "AAPL";
        final String strategy = "TrendFollowing";

        try {
            JsonArray array = JSON.readJSON(symbol, "bars");
            List<Bar> barList = new ArrayList<>();
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

            Strategy strategyInstance = new StrategyFactory().useStrategy(strategy);
            System.out.println("Running strategy: " + strategy + " on " + symbol);

            // Execute strategy on the list of bars starting from the minimum (20) days to
            // the recent day
            barList.stream().skip(20).forEach(bar -> {
                List<Bar> prices = barList.subList(0, barList.indexOf(bar) + 1);
                strategyInstance.execute(prices);
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}