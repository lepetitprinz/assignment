package com.musinsa.assignment.exception;

import com.musinsa.assignment.common.exception.support.CustomException;
import com.musinsa.assignment.common.exception.support.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor
public enum BrandErrorCode implements ErrorCode {
    MIN_PRICE_BRAND_NO_CONTENT("There is no brand that toal prices of all categories is minimum",
        HttpStatus.NO_CONTENT),
    BRAND_NOT_FOUND("Brand not found", HttpStatus.NOT_FOUND),
    BRAND_ALREADY_EXISTS("Brand is already exists", HttpStatus.CONFLICT),
    DEFAULT("here is no brand satisfying that condition.", HttpStatus.NO_CONTENT);

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
    public BrandException exception() {
        return new BrandException(this);
    }

    @Override
    public BrandException exception(Throwable cause) {
        return new BrandException(this, cause);
    }

    @Override
    public RuntimeException exception(Runnable action) {
        return new BrandException(this, action);
    }

    @Override
    public RuntimeException exception(Runnable action, Throwable cause) {
        return new BrandException(this, action, cause);
    }

    @Override
    public RuntimeException exception(Supplier<Map<String, Object>> payloadSupplier) {
        return new BrandException(this, payloadSupplier);
    }

    @Override
    public RuntimeException exception(Supplier<Map<String, Object>> payloadSupplier, Throwable cause) {
        return new CustomException(this, payloadSupplier, cause);
    }
}
