package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalListDto {

    private int rentalId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String cityOfPickUpLocation_CityName;
    private String cityOfReturnLocation_CityName;

    private int carCarId;
}
