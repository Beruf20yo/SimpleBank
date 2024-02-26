package org.example.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition="TEXT", nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Date dateOpened;

    @Column(columnDefinition="TEXT", nullable = true)
    private String accountDetails;

    @Column(nullable = false)
    private BigDecimal accountBalance;

    @ManyToOne
    @JoinColumn(name = "account_type_id", nullable = false)
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private List<Transaction> recipientList;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Transaction> senderList;

    @OneToMany(mappedBy = "account")
    private List<Bill> bills;


}
