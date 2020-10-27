package by.radzivon.analyzer.service;

import by.radzivon.analyzer.entity.Transaction;
import by.radzivon.analyzer.model.AnalysisInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    void saveTransactionFromFile(MultipartFile file) throws IOException;

    List<Transaction> findAll();

    AnalysisInfo analyze(LocalDateTime fromDate, LocalDateTime toDate, String merchant);
}
