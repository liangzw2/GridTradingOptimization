package com.gridtrading.core;

// VolatilityCalculator.java
import com.gridtrading.data.PriceData;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import java.util.List;

public class VolatilityCalculator {

    public static VolatilityProfile calculate(List<PriceData> data) {
        double[] returns = calculateLogReturns(data);
        return new VolatilityProfile(
                calculateHistoricalVolatility(returns),
                calculateGARCHVolatility(returns)
        );
    }

    private static double[] calculateLogReturns(List<PriceData> data) {
        double[] returns = new double[data.size() - 1];
        for (int i = 1; i < data.size(); i++) {
            BigDecimal prev = data.get(i-1).getClose();
            BigDecimal curr = data.get(i).getClose();
            returns[i-1] = Math.log(curr.doubleValue() / prev.doubleValue());
        }
        return returns;
    }

    private static double calculateHistoricalVolatility(double[] returns) {
        DescriptiveStatistics stats = new DescriptiveStatistics(returns);
        return stats.getStandardDeviation() * Math.sqrt(252);
    }

    private static double calculateGARCHVolatility(double[] returns) {
        GARCHModel garch = new GARCHModel(1, 1);
        garch.fit(returns);
        return garch.getForecast();
    }
}

// GARCHModel.java
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class GARCHModel {
    private final int p;
    private final int q;
    private double[] parameters;

    public GARCHModel(int p, int q) {
        this.p = p;
        this.q = q;
    }

    public void fit(double[] returns) {
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        int n = returns.length;
        double[] y = new double[n - p - q];
        double[][] x = new double[n - p - q][p + q + 1];

        // ... 复杂的时间序列数据准备
        regression.newSampleData(y, x);
        parameters = regression.estimateRegressionParameters();
    }

    public double getForecast() {
        return Math.sqrt(parameters[0] / (1 - parameters[1] - parameters[2]));
    }
}
