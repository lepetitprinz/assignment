package com.musinsa.assignment.exception;

import com.musinsa.assignment.common.exception.support.CustomException;
import com.musinsa.assignment.common.exception.support.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode {
    PRODUCT_NOT_FOUND("Product not found", HttpStatus.NOT_FOUND),
    PRODUCT_ALREADY_EXISTS("Product already exists", HttpStatus.CONFLICT),
    PRICE_NOT_EXIST("Price not set", HttpStatus.BAD_REQUEST),
    DEFAULT("There is no product in database.", HttpStatus.NOT_FOUND);

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
    public ProductException exception() {
        return new ProductException(this);
    }

    @Override
    public ProductException exception(Throwable cause) {
        return new ProductException(this, cause);
    }

    @Override
    public RuntimeException exception(Runnable action) {
        return new ProductException(this, action);
    }

    @Override
    public RuntimeException exception(Runnable action, Throwable cause) {
        return new ProductException(this, action, cause);
    }

    @Override
    public RuntimeException exception(Supplier<Map<String, Object>> payloadSupplier) {
        return new ProductException(this, payloadSupplier);
    }

    @Override
    public RuntimeException exception(Supplier<Map<String, Object>> payloadSupplier, Throwable cause) {
        return new CustomException(this, payloadSupplier, cause);
    }
}
