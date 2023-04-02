package ru.tsu.hits.userservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tsu.hits.userservice.exception.user.UnauthorizedUserException;
import ru.tsu.hits.userservice.exception.user.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(UnauthorizedUserException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApplicationException handleUserNotFound(UnauthorizedUserException exception) {
        log.error(exception.getMessage(), exception);
        return new ApplicationException(exception.getMessage(), HttpStatus.FORBIDDEN, LocalDateTime.now());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApplicationException handleUserNotFound(UserNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ApplicationException(exception.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ApplicationException> handleINotValidArgument(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);
        List<ApplicationException> exceptions = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(
                ex -> exceptions.add(
                        new ApplicationException(
                                ex.getField() + " - " + ex.getDefaultMessage(),
                                HttpStatus.BAD_REQUEST,
                                LocalDateTime.now()
                        )
                )
        );
        return exceptions;
    }
}
