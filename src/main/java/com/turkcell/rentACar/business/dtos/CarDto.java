package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {

    private int carId;
    private double dailyPrice;
    private int modelYear;
    private double kilometerInformation;
    private String description;
    private String brandName;
    private String colorName;
}
