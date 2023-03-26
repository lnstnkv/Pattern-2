package ru.tsu.hits.creditservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TARIFF_INFO")
@Data
public class TariffEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private float percentage;
}
