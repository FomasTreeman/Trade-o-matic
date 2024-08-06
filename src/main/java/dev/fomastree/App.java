package dev.fomastree;

import com.google.gson.JsonArray;

import dev.fomastree.backend.JSON;
import dev.fomastree.strategies.StrategyFactory;

public class App {
    public static void main(String[] args) {
        JsonArray array = JSON.readJSON("AAPL", "bars");
        StrategyFactory strategyFactory = new StrategyFactory();
        strategyFactory.useStrategy("Bollinger").backTest(array, "AAPL");
    }
}