package com.musinsa.assignment.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.musinsa.assignment.common.exception.response.ErrorResponse;
import com.musinsa.assignment.common.exception.support.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * CustomException Handling
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(
        CustomException exception,
        HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        HttpStatus httpStatus = exception
            .getErrorCode()
            .httpStatus();

        exception.executeOnError();
        var payload = exception.getPayload();

        ErrorResponse response = ErrorResponse.of(exception, path, payload);

        return ResponseEntity
            .status(httpStatus)
            .body(response);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException exception,
        HttpServletRequest request
    ) {
        BindingResult bindingResult = exception.getBindingResult();
        record Response(
            Instant timestamp,
            int status,
            String error,
            @JsonInclude(Include.NON_NULL)
            String message,
            String path
        ) {}

        @SuppressWarnings("ConstantConditions")
        String message = bindingResult.hasFieldErrors() ?
            bindingResult.getFieldError().getDefaultMessage()
            : null;

        Response body = new Response(
            Instant.now(),
            exception.getStatusCode().value(),
            exception.getStatusCode().toString(),
            message,
            request.getRequestURI()
        );

        return ResponseEntity
            .badRequest()
            .body(body);
    }
}

