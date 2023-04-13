package ru.tsu.hits.creditservice.dto;

import lombok.Data;

@Data
public class PaymentRequest {

    private Integer creditId;

    private String accountId;

    private Float amount;
}
