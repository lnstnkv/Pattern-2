package ru.tsu.hits.creditservice.dto;

import lombok.Data;

@Data
public class PaymentRequest {

    private Integer creditId;

    private Float payment;
}
