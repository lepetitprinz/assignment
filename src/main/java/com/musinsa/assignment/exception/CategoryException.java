package com.musinsa.assignment.exception;

import com.musinsa.assignment.common.exception.support.CustomException;
import com.musinsa.assignment.common.exception.support.ErrorCode;

import java.util.Map;
import java.util.function.Supplier;

public class CategoryException extends CustomException {
    public CategoryException() {
        super();
    }

    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CategoryException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public CategoryException(ErrorCode errorCode, Runnable onError) {
        super(errorCode, onError);
    }

    public CategoryException(ErrorCode errorCode, Runnable onError, Throwable cause) {
        super(errorCode, onError, cause);
    }

    public CategoryException(ErrorCode errorCode, Supplier<Map<String, Object>> payloadSupplier) {
        super(errorCode, payloadSupplier);
    }

    public CategoryException(ErrorCode errorCode, Supplier<Map<String, Object>> payloadSupplier, Throwable cause) {
        super(errorCode, payloadSupplier, cause);
    }
}
