package org.example.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date dateCreated;

    @Column(columnDefinition="TEXT", nullable = false)
    private String service;

    @Column(columnDefinition="TEXT", nullable = true)
    private String details;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(columnDefinition="TEXT", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "credit_account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

}