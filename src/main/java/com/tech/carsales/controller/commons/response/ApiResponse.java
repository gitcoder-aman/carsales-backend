package com.tech.carsales.controller.commons.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse<T> {

    private String message;
    private boolean success;
    private T data;
    private int statusCode;
    private LocalDateTime timestamp;

    public ApiResponse(boolean success, String message, T data, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.success = success;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }


}
