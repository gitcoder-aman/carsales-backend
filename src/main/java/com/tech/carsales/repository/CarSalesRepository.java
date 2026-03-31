package com.tech.carsales.repository;

import com.tech.carsales.dto.YearlyCountDto;
import com.tech.carsales.entity.CarSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarSalesRepository extends JpaRepository<CarSales, Long> {

    boolean existsByCarNumber(String carNumber);

    @Query("""
                SELECT new com.tech.carsales.dto.YearlyCountDto(c.year,count(c))
                            from CarSales c Group by c.year Order by c.year
            """)
    List<YearlyCountDto> getYearlyCount();
}
