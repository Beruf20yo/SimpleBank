package org.example.domain.repository;

import org.example.domain.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}
