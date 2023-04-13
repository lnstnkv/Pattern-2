package ru.tsu.hits.creditservice.dto;

import lombok.Data;

@Data
public class CreateUpdateCreditRequest {

    private int creditDuration;

    private Float creditAmount;

    private String tariffName;

    private String userId;

    private String accountId;
}
