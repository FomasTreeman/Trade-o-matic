package dev.fomastree.strategies;

public class StrategyFactory {
    public Strategy useStrategy(String strategyType) {
        if (strategyType.equals("TrendFollowing")) {
            return new TrendFollowing();
        } else if (strategyType.equals("Bollinger")) {
            return new Bollinger(20);
        }
        return null;
    }
}
