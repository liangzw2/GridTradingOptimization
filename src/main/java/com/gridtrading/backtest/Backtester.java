package com.gridtrading.backtest;

// Backtester.java
import com.gridtrading.core.GridStrategy;
import com.gridtrading.data.PriceData;

import java.math.BigDecimal;
import java.util.List;

public class Backtester {
    public BacktestResult runBacktest(GridStrategy strategy, List<PriceData> testData) {
        BigDecimal initialCapital = strategy.getInvestment();
        BigDecimal currentCapital = initialCapital;
        int trades = 0;

        for (PriceData data : testData) {
            // 执行网格交易逻辑
            // 详细交易信号生成和损益计算
            trades++;
        }

        return new BacktestResult(
                currentCapital.subtract(initialCapital),
                (currentCapital.subtract(initialCapital))
                        .divide(initialCapital, 4, RoundingMode.HALF_UP),
                trades
        );
    }
}

// BacktestResult.java
public class BacktestResult {
    private final BigDecimal profit;
    private final BigDecimal returnRate;
    private final int tradeCount;

    // ... Constructor/Getters
}
