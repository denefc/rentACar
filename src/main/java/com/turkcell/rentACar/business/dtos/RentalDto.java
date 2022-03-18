package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {

    private LocalDate startDate;

    private LocalDate endDate;

    private int cityOfPickUpLocationId;

    private int cityOfReturnLocationId;

    private double rentStartKilometer;

    private double returnKilometer;

    private CarDto carDto;



}
