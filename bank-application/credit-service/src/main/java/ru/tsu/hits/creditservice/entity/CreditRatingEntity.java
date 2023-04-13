package ru.tsu.hits.creditservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CREDIT_RATING")
@Data
public class CreditRatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userId;

    private Float returnProbability;
}
