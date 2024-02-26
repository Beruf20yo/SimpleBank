package org.example.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date date;

    @Column(columnDefinition="TEXT", nullable = false)
    private String location;

    @Column(columnDefinition="TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean confirmed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
