package org.example.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition="TEXT", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "accountType")
    private List<Account> accounts;
}
