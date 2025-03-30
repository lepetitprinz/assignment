package com.musinsa.assignment.common.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.musinsa.assignment.common.exception.support.CustomException;
import com.musinsa.assignment.common.util.text.TextCaseUtil;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Builder
public record ErrorResponse(
    String code,
    Integer status,
    String name,
    String message,
    String error,
    @JsonInclude(Include.NON_EMPTY) List<ApiError> cause,
    Instant timestamp,
    @JsonInclude(Include.NON_NULL) String path,
    @JsonInclude(Include.NON_EMPTY) Map<String, Object> payload
) {
    public static ErrorResponse of(CustomException exception, String path, Map<String, Object> payload) {
        var errorCode = exception.getErrorCode();
        var errorName = exception.getClass().getName();
        var error = TextCaseUtil.capitalizeAndSaveUpperSnakeCase(
            errorCode.httpStatus().name()
        );
        errorName = errorName.substring(errorName.lastIndexOf('.') + 1);

        return ErrorResponse.builder()
            .code(errorCode.name())
            .status(errorCode.httpStatus().value())
            .name(errorName)
            .message(exception.getMessage())
            .error(error)
            .cause(ApiError.listOfCauseApiError(exception))
            .path(path)
            .payload(payload)
            .build();
    }

    public ErrorResponse {
        if (code == null) {
            code = "API_ERROR";
        }

        if (status == null) {
            status = 500;
        }

        if (name == null) {
            name = "ApiError";
        }

        if (message == null || message.isBlank()) {
            message = "API 오류";
        }

        if (timestamp == null) {
            timestamp = Instant.now();
        }

        if (path == null) {
            path = "about:blank";
        }

        if (payload != null && payload.isEmpty()) {
            payload = null;
        }
    }
}
