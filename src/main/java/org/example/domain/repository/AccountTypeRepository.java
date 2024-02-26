package org.example.domain.repository;

import org.example.domain.model.AccountType;
import org.springframework.data.repository.CrudRepository;

public interface AccountTypeRepository extends CrudRepository<AccountType, Integer> {
}
