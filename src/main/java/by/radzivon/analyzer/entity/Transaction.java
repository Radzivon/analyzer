package by.radzivon.analyzer.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    private String id;
    private LocalDateTime date;
    private BigDecimal amount;
    private String merchant;
    private TransactionType type;
    private String relatedTransaction;
}
