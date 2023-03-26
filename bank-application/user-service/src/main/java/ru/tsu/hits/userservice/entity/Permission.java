package ru.tsu.hits.userservice.entity;

import lombok.Getter;

@Getter
public enum Permission {

    CREATE_USER("CREATE_USER"),
    SEE_OPERATIONS_HISTORY("SEE_OPERATIONS_HISTORY");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
