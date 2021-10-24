package com.godel.simplecrud.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage validateErrorHandler(ConstraintViolationException exception, HttpServletRequest request) {
        int status = HttpStatus.BAD_REQUEST.value();
        String description = request.getQueryString() == null ? request.getRequestURI()
                : request.getRequestURI() + ", params: " +  request.getQueryString();

        StringBuilder messages = new StringBuilder();
        exception.getConstraintViolations()
                .forEach(el -> messages.append(el.getMessage()).append("\n"));
        String stringMessage = messages.replace(messages.length() - 1, messages.length(), "").toString();

        log.error("HTTP status: {}, path: {}", status, description, exception);

        return new ErrorMessage(status, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), stringMessage, description);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage validateRequestBody(MethodArgumentNotValidException exception, HttpServletRequest request) {
        int status = HttpStatus.BAD_REQUEST.value();
        String description = request.getQueryString() == null ? request.getRequestURI()
                : request.getRequestURI() + ", params: " +  request.getQueryString();

        StringBuilder messages = new StringBuilder();
        exception.getFieldErrors()
                .forEach(el -> messages.append(el.getDefaultMessage()).append("\n"));
        String stringMessage = messages.replace(messages.length() - 1, messages.length(), "").toString();

        log.error("HTTP status: {}, path: {}", status, description, exception);
        return new ErrorMessage(status, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), stringMessage, description);
    }

    @ExceptionHandler(EmployeeServiceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage notFoundException(EmployeeServiceNotFoundException exception, WebRequest request) {
        int status = HttpStatus.NOT_FOUND.value();
        String description = request.getDescription(false);

        log.error("HTTP status: {}, path: {}", status, description, exception);

        return new ErrorMessage(status, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), exception.getMessage(), description);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage globalExceptionHandler(Exception exception, WebRequest request) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String description = request.getDescription(false);

        log.error("HTTP status: {}, path: {}", status, description, exception);

        return new ErrorMessage(status, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), exception.getMessage(), description);
    }

}