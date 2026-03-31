package com.tech.carsales.controller;

import com.tech.carsales.controller.commons.response.ApiResponse;
import com.tech.carsales.dto.YearlyCountDto;
import com.tech.carsales.dto.upload.UploadSalesResponse;
import com.tech.carsales.service.CarSalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/car-sales")
@RequiredArgsConstructor
public class CarSalesController {

    private final CarSalesService carSalesService;

    @PostMapping("/upload-csv")
    public ResponseEntity<ApiResponse<UploadSalesResponse>> uploadFile(@RequestParam("file") MultipartFile file) {

        //file is available or not
        if (file.isEmpty()) {
            UploadSalesResponse uploadSalesResponse = new UploadSalesResponse(0, 0, 0);

            ApiResponse<UploadSalesResponse> apiResponse = new ApiResponse<>(
                    false,
                    "File is Empty",
                    uploadSalesResponse,
                    HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        UploadSalesResponse uploadSalesResponse = carSalesService.uploadCsv(file);
        ApiResponse<UploadSalesResponse> apiResponse = getApiResponse(uploadSalesResponse);

        return ResponseEntity.ok(apiResponse);
    }

    private static ApiResponse<UploadSalesResponse>getApiResponse(UploadSalesResponse response){

        String message;
        boolean success;

        if(response.getFailedCount() == 0){
            message = "All Record Successfully";
            success = true;
        }else if(response.getSuccessCount() == 0){
            message = "All Record failed to upload";
            success = false;
        }else{
            message = "Uploaded with some errors "+response.getFailedCount();
            success = false;
        }

        return new ApiResponse<UploadSalesResponse>(success,message,response,HttpStatus.OK.value());
    }

    @GetMapping("/yearly-count")
    public ResponseEntity<ApiResponse<List<YearlyCountDto>>>yearlyCount(){
        List<YearlyCountDto> yearlyCarsCount = carSalesService.getYearlyCarsCount();
        ApiResponse<List<YearlyCountDto>>response = new ApiResponse<List<YearlyCountDto>>(
                true,
                "Data Read Successfully",
                yearlyCarsCount,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }
}
