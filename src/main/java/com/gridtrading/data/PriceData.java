package com.gridtrading.data;

// PriceData.java
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceData {
    private final String symbol;
    private final LocalDateTime timestamp;
    private final BigDecimal open;
    private final BigDecimal high;
    private final BigDecimal low;
    private final BigDecimal close;
    private final BigDecimal volume;

    public PriceData(String symbol, LocalDateTime timestamp,
                     BigDecimal open, BigDecimal high,
                     BigDecimal low, BigDecimal close,
                     BigDecimal volume) {
        // ... 参数校验和初始化
    }

    // Getters...
    public BigDecimal getMidPrice() {
        return high.add(low).divide(BigDecimal.valueOf(2), 8, RoundingMode.HALF_UP);
    }

    public BigDecimal getPriceRange() {
        return high.subtract(low);
    }
}
