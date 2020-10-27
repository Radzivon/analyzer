package by.radzivon.analyzer.controller;

import by.radzivon.analyzer.entity.Transaction;
import by.radzivon.analyzer.model.ResponseMessage;
import by.radzivon.analyzer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            transactionService.saveTransactionFromFile(file);
            return ResponseEntity.ok(new ResponseMessage("file upload successful"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Could not upload the file"));
        }
    }

    @GetMapping("/all")
    public List<Transaction> getAll() {
        return transactionService.findAll();
    }

    @GetMapping("/analyze")
    public ResponseEntity<?> analyze(@PathParam("fromDate")
                                     @DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss")
                                             LocalDateTime fromDate,
                                     @PathParam("toDate")
                                     @DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss")
                                             LocalDateTime toDate,
                                     @PathParam("merchant") String merchant) {

        return ResponseEntity.ok(transactionService.analyze(fromDate, toDate, merchant));
    }
}
