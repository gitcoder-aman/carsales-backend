package com.tech.carsales.controller.commons.response;

import com.tech.carsales.dto.upload.UploadSalesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<UploadSalesResponse>> handleAllException(Exception ex) {
        UploadSalesResponse uploadSalesResponse = new UploadSalesResponse(0, 0, 0);
        ApiResponse<UploadSalesResponse> apiResponse = new ApiResponse<>(
                false,
                ex.getMessage(),
                uploadSalesResponse,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
