package com.musinsa.assignment.exception;

import com.musinsa.assignment.common.exception.support.CustomException;
import com.musinsa.assignment.common.exception.support.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor
public enum CategoryErrorCode implements ErrorCode {
    CATEGORY_NOT_FOUND("Category not found", HttpStatus.NOT_FOUND),
    DEFAULT("Insert the existing category.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    @Override
    public String message() {
        return message;
    }

    @Override
    public HttpStatus httpStatus() {
        return status;
    }

    @Override
    public CategoryException exception() {
        return new CategoryException(this);
    }

    @Override
    public CategoryException exception(Throwable cause) {
        return new CategoryException(this, cause);
    }

    @Override
    public RuntimeException exception(Runnable action) {
        return new CategoryException(this, action);
    }

    @Override
    public RuntimeException exception(Runnable action, Throwable cause) {
        return new CategoryException(this, action, cause);
    }

    @Override
    public RuntimeException exception(Supplier<Map<String, Object>> payloadSupplier) {
        return new CategoryException(this, payloadSupplier);
    }

    @Override
    public RuntimeException exception(Supplier<Map<String, Object>> payloadSupplier, Throwable cause) {
        return new CustomException(this, payloadSupplier, cause);
    }
}