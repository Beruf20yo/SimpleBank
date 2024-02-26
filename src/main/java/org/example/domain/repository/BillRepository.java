package org.example.domain.repository;

import org.example.domain.model.Bill;
import org.springframework.data.repository.CrudRepository;

public interface BillRepository extends CrudRepository<Bill, Integer> {
}
