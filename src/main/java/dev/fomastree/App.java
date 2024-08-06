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
        JsonArray array = JSON.readJSON("AAPL", "bars");
        StrategyFactory strategyFactory = new StrategyFactory();
        strategyFactory.useStrategy("Bollinger").backTest(array, "AAPL");
    }
}