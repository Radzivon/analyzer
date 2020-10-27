package by.radzivon.analyzer.repository;

import by.radzivon.analyzer.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {
    List<Transaction> findAll();

    List<Transaction> findAllByMerchantAndDateIsBetween(String merchant, LocalDateTime fromDate, LocalDateTime toDate);

    Transaction save(Transaction transaction);

    @Override
    <S extends Transaction> Iterable<S> saveAll(Iterable<S> iterable);
}