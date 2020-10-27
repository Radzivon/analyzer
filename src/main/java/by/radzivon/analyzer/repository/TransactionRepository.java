package by.radzivon.analyzer.repository;

import by.radzivon.analyzer.entity.Transaction;
import by.radzivon.analyzer.entity.TransactionType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {
    List<Transaction> findAll();

    List<Transaction> findAllByMerchantAndDateIsBetweenAndType(String merchant, LocalDateTime fromDate, LocalDateTime toDate, TransactionType transactionType);

    List<Transaction> findAllByType(TransactionType transactionType);

    Transaction save(Transaction transaction);

    @Override
    <S extends Transaction> Iterable<S> saveAll(Iterable<S> iterable);
}
