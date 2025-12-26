package com.khanghn.careerspark_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseObject<T> (
        String code,
        String message,
        T data
) {
    public static <T> ResponseObject<T> success(String message) {
        return new ResponseObject<>("SUCCESS", message, null);
    }

    public static <T> ResponseObject<T> success(String message, T data) {
        return new ResponseObject<>("SUCCESS", message, data);
    }

    public static <T> ResponseObject<T> error(String code, String message) {
        return new ResponseObject<>(code, message, null);
    }
}
