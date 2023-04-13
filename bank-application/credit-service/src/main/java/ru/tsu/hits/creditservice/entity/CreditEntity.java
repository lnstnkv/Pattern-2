package ru.tsu.hits.creditservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "CREDIT_INFO")
@Data
public class CreditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Timestamp creditStart;

    private Integer creditDuration;

    private Float creditAmount; // Я вкурсе деньги нельзя так хранить, но в рамках лабороторной сойдёт

    private String tariff;

    private Float debt;

    private String userId;

    private String accountId;

    private Float payed;

    private boolean closed;
}
