package dev.fomastree.strategies;

public class StrategyFactory {
    public Strategy useStrategy(String strategyType) {
        if ("A".equals(strategyType)) {
            return new TrendFollowing();
        }
        return null;
    }
}
