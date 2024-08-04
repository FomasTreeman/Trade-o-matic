package dev.fomastree.strategies;

import java.util.ArrayList;
import java.util.List;

public class TrendFollowing implements Strategy {
    private List<Double> closingPrices;

    @Override
    public void execute(List<Double> closingPrices) {
        final int shortPeriod = 10;
        final int  longPeriod = 50;

        if (closingPrices.size() < longPeriod) {
            System.out.println("Insufficient data for the specified period.");
            return;
        }

        List<Double> shortSMA = calculateSMA(shortPeriod);
        List<Double> longSMA = calculateSMA(longPeriod);

        for (int i = longPeriod; i < closingPrices.size(); i++) {
            if (shortSMA.get(i) > longSMA.get(i) && shortSMA.get(i - 1) <= longSMA.get(i - 1)) {
                System.out.println("Buy signal at index " + i);
            } else if (shortSMA.get(i) < longSMA.get(i) && shortSMA.get(i - 1) >= longSMA.get(i - 1)) {
                System.out.println("Sell signal at index " + i);
            }
        }
    }

    private List<Double> calculateSMA(int period) {
        List<Double> smaValues = new ArrayList<>();
        for (int i = period - 1; i < closingPrices.size(); i++) {
            double sum = 0;
            for (int j = i - period + 1; j <= i; j++) {
                sum += closingPrices.get(j);
            }
            smaValues.add(sum / period);
        }
        return smaValues;
    }
}
