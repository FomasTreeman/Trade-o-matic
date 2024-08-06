package dev.fomastree.strategies;

import java.util.List;

import com.google.gson.JsonArray;

import dev.fomastree.backend.Bar;

public interface Strategy {
    Signal execute(List<Bar> values);
    void backTest(JsonArray array, String symbol);
}