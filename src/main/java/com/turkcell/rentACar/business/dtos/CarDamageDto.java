package com.turkcell.rentACar.business.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDamageDto {

    private int id;

    private String description;

    private int carId;
}
