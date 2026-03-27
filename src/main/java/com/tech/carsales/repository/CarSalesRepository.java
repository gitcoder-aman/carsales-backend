package com.tech.carsales.repository;

import com.tech.carsales.entity.CarSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarSalesRepository extends JpaRepository<CarSales,Long> {

    boolean existsByCarNumber(String carNumber);
}
