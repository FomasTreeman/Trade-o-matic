package dev.fomastree.backend;

import java.util.Date;

public class Bar {
    private double closingPrice;
    private double highPrice;
    private double lowPrice;
    private int numberOfTrades;
    private double openingPrice;
    private Date timestamp;
    private long volume;
    private double volumeWeightedAveragePrice;

    public Bar(double closingPrice, double highPrice, double lowPrice, int numberOfTrades, double openingPrice, Date timestamp, long volume, double volumeWeightedAveragePrice) {
        this.closingPrice = closingPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.numberOfTrades = numberOfTrades;
        this.openingPrice = openingPrice;
        this.timestamp = timestamp;
        this.volume = volume;
        this.volumeWeightedAveragePrice = volumeWeightedAveragePrice;
    }

    public double getClosingPrice() {
        return closingPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public int getNumberOfTrades() {
        return numberOfTrades;
    }

    public double getOpeningPrice() {
        return openingPrice;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public long getVolume() {
        return volume;
    }

    public double getVolumeWeightedAveragePrice() {
        return volumeWeightedAveragePrice;
    }
}
