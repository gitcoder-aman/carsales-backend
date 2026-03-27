package com.tech.carsales.service;

import com.tech.carsales.dto.upload.UploadSalesResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CarSalesService {

    UploadSalesResponse uploadCsv(MultipartFile file);
}
