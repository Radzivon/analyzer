package by.radzivon.analyzer.controller;

import by.radzivon.analyzer.entity.Transaction;
import by.radzivon.analyzer.model.AnalysisInfo;
import by.radzivon.analyzer.model.ResponseMessage;
import by.radzivon.analyzer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/analyze")
    public AnalysisInfo analyze(@PathParam("fromDate") String fromDate,
                                @PathParam("toDate") String toDate,
                                @PathParam("merchant") String merchant) {

        LocalDateTime fromDateTime = LocalDateTime.parse(fromDate, formatter);
        LocalDateTime toDateTime = LocalDateTime.parse(toDate, formatter);
        return transactionService.analyze(fromDateTime, toDateTime, merchant);
    }
}
