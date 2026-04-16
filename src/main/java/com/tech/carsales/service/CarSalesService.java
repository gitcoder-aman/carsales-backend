package com.tech.carsales.service;

import com.tech.carsales.dto.MonthlyCountDto;
import com.tech.carsales.dto.YearlyCountDto;
import com.tech.carsales.dto.upload.UploadSalesResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarSalesService {

    UploadSalesResponse uploadCsv(MultipartFile file);

    List<YearlyCountDto>getYearlyCarsCount();

    List<MonthlyCountDto>getMonthlyCountByYear(int year);
}
