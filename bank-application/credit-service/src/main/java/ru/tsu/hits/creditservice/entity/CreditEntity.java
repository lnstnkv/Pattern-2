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

    private Float creditAmount; // Я вкурсе что деньги так хранят, но в рамках лабороторной сойдёт

    private String tariff;

    private Float debt;

    private Integer userId;

    private Float payed;

    private boolean closed;
}
