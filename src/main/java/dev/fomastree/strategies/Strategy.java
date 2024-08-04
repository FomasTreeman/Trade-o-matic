package dev.fomastree.strategies;

import java.util.List;

public interface Strategy {
    void execute(List<Double> values);
}