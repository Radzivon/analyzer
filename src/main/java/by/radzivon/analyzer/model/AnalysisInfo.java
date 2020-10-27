package by.radzivon.analyzer.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AnalysisInfo {
    private Long numberOfTransactions;
    private BigDecimal averageTransactionValue;
}
