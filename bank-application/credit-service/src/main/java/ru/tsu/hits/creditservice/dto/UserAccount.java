package ru.tsu.hits.creditservice.dto;

import lombok.Data;

@Data
public class UserAccount {

    private Account[] accounts;

    private int totalCount;
}
