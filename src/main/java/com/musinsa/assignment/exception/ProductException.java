package com.musinsa.assignment.exception;

import com.musinsa.assignment.common.exception.support.CustomException;
import com.musinsa.assignment.common.exception.support.ErrorCode;

import java.util.Map;
import java.util.function.Supplier;

public class ProductException extends CustomException {
    public ProductException() {
        super();
    }

    public ProductException(String message) {
        super(message);
    }

    public ProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ProductException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public ProductException(ErrorCode errorCode, Runnable onError) {
        super(errorCode, onError);
    }

    public ProductException(ErrorCode errorCode, Runnable onError, Throwable cause) {
        super(errorCode, onError, cause);
    }

    public ProductException(ErrorCode errorCode, Supplier<Map<String, Object>> payloadSupplier) {
        super(errorCode, payloadSupplier);
    }

    public ProductException(ErrorCode errorCode, Supplier<Map<String, Object>> payloadSupplier, Throwable cause) {
        super(errorCode, payloadSupplier, cause);
    }
}
