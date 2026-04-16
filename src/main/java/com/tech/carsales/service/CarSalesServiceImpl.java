package com.tech.carsales.service;

import com.tech.carsales.dto.MonthlyCountDto;
import com.tech.carsales.dto.YearlyCountDto;
import com.tech.carsales.dto.upload.UploadSalesResponse;
import com.tech.carsales.entity.CarSales;
import com.tech.carsales.repository.CarSalesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarSalesServiceImpl implements CarSalesService {

    private final CarSalesRepository carSalesRepository;

    @Override
    public UploadSalesResponse uploadCsv(MultipartFile file) {

        List<CarSales> carSalesList = new ArrayList<>();
        int failCount = 0;
        int totalRecords = 0;


        try {
            InputStream inputStream = file.getInputStream(); //raw bytes
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            //CSV format
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true) //skip (not treated as data of 1st row in csv file)
                    .setIgnoreHeaderCase(true)  // case-insensitive
                    .setTrim(true)  //trim space
                    .build();

            CSVParser csvParser = CSVParser.parse(bufferedReader, csvFormat);

            for (CSVRecord record : csvParser){
                totalRecords++;

                try {
                    String carNumber = record.get("Car Number");
                    boolean exists = carSalesRepository.existsByCarNumber(carNumber);
                    if (exists) {
                        failCount++;
                        log.info("Duplicate Data found:{}", carNumber);
                        continue;
                    }
                    CarSales carSales = new CarSales();
                    carSales.setCarNumber(record.get("Car Number"));
                    carSales.setBrand(record.get("Brand"));
                    carSales.setModel(record.get("Model"));
                    carSales.setColor(record.get("Color"));
                    carSales.setYear(Integer.parseInt(record.get("Year")));
                    carSales.setDateOfPurchase(LocalDate.parse(record.get("Date of Purchase"), DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                    carSales.setTimeOfPurchase(LocalTime.parse(record.get("Time of Purchase")));
                    carSales.setPrice(Long.parseLong(record.get("Price")));
                    carSales.setMileage(Double.parseDouble(record.get("Mileage")));
                    carSales.setEngine(Integer.parseInt(record.get("Engine")));
                    carSales.setFuelType(record.get("Fuel Type"));
                    carSales.setPaymentMode(record.get("Payment Mode"));
                    carSales.setState(record.get("State"));
                    carSales.setCity(record.get("City"));
                    carSales.setCustomerName(record.get("Customer Name"));
                    carSales.setContactNumber(record.get("Contact Number"));
                    carSales.setEmail(record.get("Email"));
                    carSales.setWarrantyPeriod(Integer.parseInt(record.get("Warranty Period")));

                    carSalesList.add(carSales);

                } catch (Exception e) {
                    failCount++;
                    log.error(
                            "Failed to process row {}: {}",
                            record.getRecordNumber(),
                            e.getMessage(),
                            e
                    );
                }
            }
            bufferedReader.close();
            if(!carSalesList.isEmpty()){
                carSalesRepository.saveAll(carSalesList);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to Parse CSV: "+e.getMessage());
        }

        int successCount = totalRecords - failCount;
        return new UploadSalesResponse(totalRecords,successCount,failCount);
    }

    @Override
    public List<YearlyCountDto> getYearlyCarsCount() {
        return carSalesRepository.getYearlyCount();
    }

    @Override
    public List<MonthlyCountDto> getMonthlyCountByYear(int year) {
        List<MonthlyCountDto> data =  carSalesRepository.getMonthlyCountByYear(year);
        Map<Integer,Long> map = data.stream()
                .collect(Collectors.toMap(
                        MonthlyCountDto::month,
                        MonthlyCountDto::count
                ));
        List<MonthlyCountDto>result = new ArrayList<>();
        for (int i = 1; i <= 12; i++){
            result.add(new MonthlyCountDto(i,map.getOrDefault(1,0L)));
        }
        return result;
    }
}
