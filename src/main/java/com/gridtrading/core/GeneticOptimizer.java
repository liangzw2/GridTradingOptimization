package com.gridtrading.core;

// GeneticOptimizer.java
import com.gridtrading.data.PriceData;

import java.math.BigDecimal;
import java.util.*;

public class GeneticOptimizer {
    private static final int POPULATION_SIZE = 100;
    private static final double MUTATION_RATE = 0.01;
    private static final int MAX_GENERATIONS = 100;

    private final List<PriceData> historicalData;
    private final BigDecimal investment;

    public GeneticOptimizer(List<PriceData> historicalData, BigDecimal investment) {
        this.historicalData = historicalData;
        this.investment = investment;
    }

    public GridStrategy optimize() {
        List<GridStrategy> population = initializePopulation();
        for (int gen = 0; gen < MAX_GENERATIONS; gen++) {
            population = evolvePopulation(population);
        }
        return Collections.max(population, Comparator.comparing(GridStrategy::getFitness));
    }

    private List<GridStrategy> initializePopulation() {
        List<GridStrategy> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(GridStrategy.generateRandom(investment));
        }
        return population;
    }

    private List<GridStrategy> evolvePopulation(List<GridStrategy> population) {
        List<GridStrategy> newPopulation = new ArrayList<>();
        // 精英保留
        newPopulation.add(Collections.max(population, Comparator.comparing(GridStrategy::getFitness)));

        // 交叉和变异
        while (newPopulation.size() < POPULATION_SIZE) {
            GridStrategy parent1 = selectParent(population);
            GridStrategy parent2 = selectParent(population);
            GridStrategy child = crossover(parent1, parent2);
            mutate(child);
            newPopulation.add(child);
        }
        return newPopulation;
    }

    private GridStrategy selectParent(List<GridStrategy> population) {
        // 锦标赛选择实现
    }

    private GridStrategy crossover(GridStrategy parent1, GridStrategy parent2) {
        // 单点交叉实现
    }

    private void mutate(GridStrategy strategy) {
        // 高斯变异实现
    }
}

// GridStrategy.java
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class GridStrategy {
    private BigDecimal lowerBound;
    private BigDecimal upperBound;
    private int gridCount;
    private BigDecimal investment;
    private BigDecimal fitness;

    public static GridStrategy generateRandom(BigDecimal investment) {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        BigDecimal lower = BigDecimal.valueOf(rand.nextDouble(8000, 10000))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal upper = lower.multiply(BigDecimal.valueOf(rand.nextDouble(1.05, 1.2)));
        return new GridStrategy(lower, upper, rand.nextInt(5, 50), investment);
    }

    public BigDecimal calculateFitness(List<PriceData> data) {
        // 详细收益计算逻辑
        return fitness;
    }

    // ... Getters/Setters
}
