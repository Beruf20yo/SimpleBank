package org.example.domain.repository;


import org.example.domain.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
}
