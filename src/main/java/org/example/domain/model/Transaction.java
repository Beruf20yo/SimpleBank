package org.example.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date date;

    @Column(columnDefinition="TEXT", nullable = true)
    private String details;

    @Column(nullable = false)
    private BigDecimal transferAmount;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false, columnDefinition = "Integer Check (recipient_id != sender_id)")
    private Account recipient;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Account sender;

    public Transaction(Date date, String details, BigDecimal amount, Account recipient, Account sender) {
        this.date = date;
        this.details = details;
        this.transferAmount = amount;
        this.recipient = recipient;
        this.sender = sender;
    }

}
