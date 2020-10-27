package by.radzivon.analyzer.service.impl;

import by.radzivon.analyzer.entity.Transaction;
import by.radzivon.analyzer.entity.TransactionType;
import by.radzivon.analyzer.model.AnalysisInfo;
import by.radzivon.analyzer.repository.TransactionRepository;
import by.radzivon.analyzer.service.ExcelService;
import by.radzivon.analyzer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private ExcelService excelService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, ExcelService excelService) {
        this.transactionRepository = transactionRepository;
        this.excelService = excelService;
    }

    @Override
    public void saveTransactionFromFile(MultipartFile file) throws IOException {
        List<Transaction> transactions = excelService.parseFile(file.getInputStream());
        transactionRepository.saveAll(transactions);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public AnalysisInfo analyze(LocalDateTime fromDate, LocalDateTime toDate, String merchant) {

        List<Transaction> transactions =
                transactionRepository.findAllByMerchantAndDateIsBetweenAndType(merchant, fromDate, toDate, TransactionType.PAYMENT);

        List<String> reversalTransactions =
                transactionRepository.findAllByType(TransactionType.REVERSAL).stream()
                        .map(Transaction::getRelatedTransaction)
                        .collect(Collectors.toList());

        transactions =
                transactions.stream()
                        .filter(x -> reversalTransactions.stream().noneMatch(str -> x.getId().equals(str)))
                        .collect(Collectors.toList());

        Long numberOfTransactions = transactions.stream().distinct().count();

        BigDecimal averageTransactionValue =
                transactions.stream()
                        .map(Transaction::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        return AnalysisInfo.builder()
                .numberOfTransactions(numberOfTransactions)
                .averageTransactionValue(averageTransactionValue)
                .build();
    }
}
