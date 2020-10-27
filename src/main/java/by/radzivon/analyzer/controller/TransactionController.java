package by.radzivon.analyzer.controller;

import by.radzivon.analyzer.entity.Transaction;
import by.radzivon.analyzer.model.AnalysisInfo;
import by.radzivon.analyzer.model.ResponseMessage;
import by.radzivon.analyzer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "api")
public class TransactionController {
    private TransactionService transactionService;
    private DateTimeFormatter formatter;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    }

    @PostMapping("/upload")
    public ResponseMessage uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        transactionService.saveTransactionFromFile(file);
        return new ResponseMessage("file upload successful");
    }

    @GetMapping("/all")
    public List<Transaction> getAll() {
        return transactionService.findAll();
    }

    @GetMapping("/anylize")
    public AnalysisInfo anylize(@PathParam("fromDate") String fromDateStr,
                                @PathParam("toDate") String toDateStr,
                                @PathParam("merchant") String merchant) {

        LocalDateTime fromDate = LocalDateTime.parse(fromDateStr, formatter);
        LocalDateTime toDate = LocalDateTime.parse(toDateStr, formatter);
        return transactionService.analyze(fromDate, toDate, merchant);
    }
}
