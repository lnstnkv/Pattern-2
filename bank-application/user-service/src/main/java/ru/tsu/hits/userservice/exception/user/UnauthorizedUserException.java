package ru.tsu.hits.userservice.exception.user;

import javax.naming.AuthenticationException;

public class UnauthorizedUserException extends AuthenticationException {

    public UnauthorizedUserException(String explanation) {
        super(explanation);
    }
}
