package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalListDto {

    private int rentalCarId;

    private LocalDate startDate;

    private LocalDate endDate;

    private int cityOfPickUpLocationId;

    private int cityOfReturnLocationId;

    private double rentStartKilometer;

    private double returnKilometer;

    private CarDto carDto;


}
