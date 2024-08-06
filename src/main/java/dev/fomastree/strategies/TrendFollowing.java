package dev.fomastree.strategies;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;

import dev.fomastree.backend.Bar;

public class TrendFollowing implements Strategy {
    private List<Bar> closingPrices;

    @Override
    public void backTest(JsonArray array, String symbol) {
        // TODO Auto-generated method stub
    }

    @Override
    public Signal execute(List<Bar> closingPrices) {
        final int shortPeriod = 10; // Hold shares for 10 days
        final int longPeriod = 50; // Hold shares for 50 days
        this.closingPrices = closingPrices;

        if (closingPrices.size() < longPeriod) {
            System.out.println("Insufficient data for the specified period.");
            return Signal.ERROR;
        }

        List<Double> shortSMA = calculateSMA(shortPeriod);
        List<Double> longSMA = calculateSMA(longPeriod);

        System.out.println("Short SMA: " + shortSMA);
        System.out.println("Long SMA: " + longSMA);

        // Compare the short SMA and long SMA to generate buy and sell signals
        for (int i = 1; i <= shortSMA.size();) {
            if (shortSMA.get(i) > longSMA.get(i) && shortSMA.get(i - 1) <= longSMA.get(i - 1)) {
                System.out.println("Buy signal at index " + i);
                return Signal.BUY;
            } else if (shortSMA.get(i) < longSMA.get(i) && shortSMA.get(i - 1) >= longSMA.get(i - 1)) {
                System.out.println("Sell signal at index " + i);
                return Signal.SELL;
            } else {
                System.out.println("Error " + i);
                return Signal.HOLD;
            }
        }
        return Signal.ERROR;
    }

    // Calculate the Simple Moving Average (SMA) for a given period
    // This is the average price of a share at EOD over the specified period
    private List<Double> calculateSMA(int period) {
        List<Double> smaValues = new ArrayList<Double>();
        for (int i = closingPrices.size() - period; i < closingPrices.size(); i++) {
            double sum = 0;
            for (int j = i - period + 1; j <= i; j++) {
                sum += closingPrices.get(j).getClosingPrice();
            }
            smaValues.add(sum / period);
        }
        return smaValues;
    }
}
