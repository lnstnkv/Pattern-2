package ru.tsu.hits.creditservice.dto;

import lombok.Data;

@Data
public class Account {

    private String id;
    private String ownerId;
    private Integer balance;
    private Boolean isBlocked;
    private Boolean isDeleted;
    private String currency;
}
