package org.example.domain.repository;

import org.example.domain.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    User findByUsername(String username);
}
