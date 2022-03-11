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

    private int rentalId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String cityOfPickUpLocation_CityName;
    private String cityOfReturnLocation_CityName;

    private double totalRentalPrice;
    private int carCarId;

    private List<OrderedAdditionalServiceDto> orderedAdditionalServices;
}
