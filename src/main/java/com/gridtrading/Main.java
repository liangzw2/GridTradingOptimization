package com.gridtrading;

// Main.java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gridtrading.backtest.BacktestResult;
import com.gridtrading.backtest.Backtester;
import com.gridtrading.core.GeneticOptimizer;
import com.gridtrading.core.GridStrategy;
import com.gridtrading.core.VolatilityCalculator;
import com.gridtrading.data.PriceData;
import com.gridtrading.risk.RiskManager;
import com.gridtrading.risk.RiskMetrics;

import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. 加载数据
        DataLoader loader = new DataLoader();
        List<PriceData> data = loader.loadFromCSV(Paths.get("data.csv"));

        // 2. 计算波动率
        VolatilityProfile volatility = VolatilityCalculator.calculate(data);

        // 3. 优化策略
        GeneticOptimizer optimizer = new GeneticOptimizer(data.subList(0, 1000), new BigDecimal("10000"));
        GridStrategy bestStrategy = optimizer.optimize();

        // 4. 回测验证
        Backtester backtester = new Backtester();
        BacktestResult result = backtester.runBacktest(bestStrategy, data.subList(1001, 2000));

        // 5. 风险分析
        List<BigDecimal> equityCurve = backtester.getEquityCurve();
        RiskMetrics metrics = RiskManager.calculateMetrics(equityCurve);

        // 6. 保存结果
        ResultExporter.exportToJson(bestStrategy, result, metrics);
    }
}
