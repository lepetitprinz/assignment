package com.musinsa.assignment.exception;

import com.musinsa.assignment.common.exception.support.CustomException;
import com.musinsa.assignment.common.exception.support.ErrorCode;

import java.util.Map;
import java.util.function.Supplier;

public class BrandException extends CustomException {
    public BrandException() {
        super();
    }

    public BrandException(String message) {
        super(message);
    }

    public BrandException(String message, Throwable cause) {
        super(message, cause);
    }

    public BrandException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BrandException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public BrandException(ErrorCode errorCode, Runnable onError) {
        super(errorCode, onError);
    }

    public BrandException(ErrorCode errorCode, Runnable onError, Throwable cause) {
        super(errorCode, onError, cause);
    }

    public BrandException(ErrorCode errorCode, Supplier<Map<String, Object>> payloadSupplier) {
        super(errorCode, payloadSupplier);
    }

    public BrandException(ErrorCode errorCode, Supplier<Map<String, Object>> payloadSupplier, Throwable cause) {
        super(errorCode, payloadSupplier, cause);
    }
}
