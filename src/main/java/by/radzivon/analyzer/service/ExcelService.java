package by.radzivon.analyzer.service;

import by.radzivon.analyzer.entity.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ExcelService {
    List<Transaction> parseFile(InputStream inputStream) throws IOException;
}
