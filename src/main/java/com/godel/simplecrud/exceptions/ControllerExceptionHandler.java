package com.godel.simplecrud.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(EmployeeControllerIllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInputParameters(EmployeeControllerIllegalArgumentException exception, WebRequest request) {
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        int status = HttpStatus.BAD_REQUEST.value();
        String description = request.getDescription(false);

        log.error("Happened: {}, HTTP status: {}, message: {}, path: {}", time, status, exception.getMessage(), description);

        return new ErrorMessage(status, time, exception.getMessage(), description);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage validateErrorHandler(ConstraintViolationException exception, WebRequest request) {
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        int status = HttpStatus.BAD_REQUEST.value();
        String description = request.getDescription(false);

        StringBuilder messages = new StringBuilder();
        exception.getConstraintViolations()
                .forEach(el -> messages.append(el.getMessage()).append("\n"));

        String stringMessage = messages.replace(messages.length() - 1, messages.length(), "").toString();

        log.error("Happened: {}, HTTP status: {}, message: {}, path: {}", time, status, stringMessage, description);

        return new ErrorMessage(status, time, stringMessage, description);
    }

    @ExceptionHandler(EmployeeServiceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage notFoundException(EmployeeServiceNotFoundException exception, WebRequest request) {
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        int status = HttpStatus.NOT_FOUND.value();
        String description = request.getDescription(false);

        log.error("Happened: {}, HTTP status: {}, message: {}, path: {}", time, status, exception.getMessage(), description);

        return new ErrorMessage(status, time, exception.getMessage(), description);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage globalExceptionHandler(Exception exception, WebRequest request) {
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String description = request.getDescription(false);

        log.error("Happened: {}, HTTP status: {}, message: {}, path: {}", time, status, exception.getMessage(), description);

        return new ErrorMessage(status, time, exception.getMessage(), description);
    }

}