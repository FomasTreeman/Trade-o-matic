package dev.fomastree;

import com.google.gson.JsonArray;

import dev.fomastree.backend.Alpaca;
import dev.fomastree.backend.JSON;
import dev.fomastree.strategies.StrategyFactory;

public class App {
    public static void main(String[] args) {
        
        System.out.println(args.length);

        if (args.length > 0) {
            Alpaca.getHistoricalData(args[0]);
        }

        JsonArray array = JSON.readJSON("AAPL", "bars");
        StrategyFactory strategyFactory = new StrategyFactory();
        strategyFactory.useStrategy("Bollinger").backTest(array, "AAPL");
    }
}