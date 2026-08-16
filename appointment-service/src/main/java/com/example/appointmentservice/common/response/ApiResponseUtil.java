package com.example.appointmentservice.common.response;

public class ApiResponseUtil {

    private ApiResponseUtil() {}

    // SUCCESS WITH DATA
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    // SUCCESS ONLY DATA
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message("Success")
                .data(data)
                .build();
    }

    // MESSAGE ONLY
    public static ApiResponse<String> success(String message) {
        return ApiResponse.<String>builder()
                .success(true)
                .message(message)
                .data(null)
                .build();
    }
}