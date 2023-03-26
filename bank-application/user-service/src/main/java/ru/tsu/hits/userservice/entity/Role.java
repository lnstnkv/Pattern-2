package ru.tsu.hits.userservice.entity;

import lombok.Getter;

import java.util.Set;

@Getter
public enum Role {

    CLIENT(Set.of(Permission.SEE_OPERATIONS_HISTORY)),
    EMPLOYEE(Set.of(Permission.CREATE_USER, Permission.SEE_OPERATIONS_HISTORY));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
