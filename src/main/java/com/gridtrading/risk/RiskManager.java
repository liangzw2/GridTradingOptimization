package com.gridtrading.risk;

// RiskManager.java
import java.math.BigDecimal;
import java.util.List;

public class RiskManager {
    public static RiskMetrics calculateMetrics(List<BigDecimal> equityCurve) {
        BigDecimal maxDrawdown = calculateMaxDrawdown(equityCurve);
        BigDecimal volatility = calculateVolatility(equityCurve);
        return new RiskMetrics(maxDrawdown, volatility);
    }

    private static BigDecimal calculateMaxDrawdown(List<BigDecimal> equityCurve) {
        BigDecimal peak = equityCurve.get(0);
        BigDecimal maxDrawdown = BigDecimal.ZERO;

        for (BigDecimal value : equityCurve) {
            if (value.compareTo(peak) > 0) {
                peak = value;
            }
            BigDecimal dd = peak.subtract(value).divide(peak, 4, RoundingMode.HALF_UP);
            if (dd.compareTo(maxDrawdown) > 0) {
                maxDrawdown = dd;
            }
        }
        return maxDrawdown;
    }

    private static BigDecimal calculateVolatility(List<BigDecimal> returns) {
        // 计算收益波动率
    }
}

// RiskMetrics.java
public class RiskMetrics {
    private final BigDecimal maxDrawdown;
    private final BigDecimal volatility;
    // ... Constructor/Getters
}

