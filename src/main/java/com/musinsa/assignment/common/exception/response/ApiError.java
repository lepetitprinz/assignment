package com.musinsa.assignment.common.exception.response;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public record ApiError(@NonNull String field, @NonNull String message) {
    public static List<ApiError> listOfCauseApiError(Throwable cause) {
        return List.of(normalizeCause(cause));
    }

    public static ApiError[] normalizeCause(Throwable cause) {
        int depth = 0;
        ApiError[] subErrors;
        Throwable currentCause = cause.getCause();

        while (currentCause != null) {
            currentCause = currentCause.getCause();
            depth++;
        }

        subErrors = new ApiError[depth];
        currentCause = cause;
        for (int i = 0; i < depth; i++) {
            String errorFullName = currentCause.getClass().getSimpleName();
            String field = errorFullName.substring(errorFullName.lastIndexOf('.') + 1);
            subErrors[i] = ApiError.builder()
                .field(field)
                .message(currentCause.getLocalizedMessage())
                .build();

            currentCause = currentCause.getCause();
        }

        return subErrors;
    }
}