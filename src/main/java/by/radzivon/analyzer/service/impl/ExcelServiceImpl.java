package by.radzivon.analyzer.service.impl;

import by.radzivon.analyzer.entity.Transaction;
import by.radzivon.analyzer.entity.TransactionType;
import by.radzivon.analyzer.service.ExcelService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {
    static String[] HEADERs = {"ID", "Date", "Merchant", "Type", "Related Transaction"};
    static String SHEET = "list";

    @Override
    public List<Transaction> parseFile(InputStream inputStream) throws IOException {

        Workbook workbook = new XSSFWorkbook(inputStream);

        Sheet sheet = workbook.getSheet(SHEET);
        Iterator<Row> rows = sheet.iterator();

        List<Transaction> transactions = new ArrayList<>();

        int rowNumber = 0;
        while (rows.hasNext()) {
            Row currentRow = rows.next();

            // skip header
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }

            Iterator<Cell> cellsInRow = currentRow.iterator();

            Transaction transaction = new Transaction();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            int cellIdx = 0;
            while (cellsInRow.hasNext()) {
                Cell currentCell = cellsInRow.next();

                switch (cellIdx) {
                    case 0:
                        transaction.setId(currentCell.getStringCellValue());
                        break;

                    case 1:
                        LocalDateTime dateTime = LocalDateTime.parse(currentCell.getStringCellValue(), formatter);

                        transaction.setDate(dateTime);
                        break;

                    case 2:
                        transaction.setAmount(new BigDecimal(currentCell.getNumericCellValue()));
                        break;

                    case 3:
                        transaction.setMerchant(currentCell.getStringCellValue());
                        break;

                    case 4:
                        transaction.setType(TransactionType.valueOf(currentCell.getStringCellValue()));
                        break;

                    case 5:
                        transaction.setRelatedTransaction(currentCell.getStringCellValue());
                        break;

                    default:
                        break;
                }
                cellIdx++;
            }

            transactions.add(transaction);
        }

        workbook.close();

        return transactions;
    }
}
