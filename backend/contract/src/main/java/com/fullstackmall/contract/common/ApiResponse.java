package com.fullstackmall.contract.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;
    private String traceId;
    private Instant timestamp;

    public static <T> ApiResponse<T> success(T data, String traceId) {
        return new ApiResponse<> (
                ApiCode.SUCCESS.name(),
                ApiCode.SUCCESS.defaultMessage(),
                data,
                traceId,
                Instant.now()
        );
    }
    public static <T> ApiResponse<T> error(ApiCode code, String message, T data, String traceId) {
        return new ApiResponse<>(
                code.name(),
                message,
                data,
                traceId,
                Instant.now()
        );
    }
}
