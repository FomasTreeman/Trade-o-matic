package dev.fomastree.backend;

import java.util.List;

public class Trade {
    private List<String> condition;
    private int id;
    private double price;
    private int quantity;
    private String timestamp;
    private String exchangeCode;
    private String tradeStatus;

    // Add getters and setters for the fields
    public Trade(List<String> condition, int id, double price, int quantity, String timestamp, String exchangeCode, String tradeStatus) {
        this.condition = condition;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = timestamp;
        this.exchangeCode = exchangeCode;
        this.tradeStatus = tradeStatus;
    }

    public List<String> getCondition() {
        return condition;
    }

    public int getID() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }
}