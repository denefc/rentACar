package com.turkcell.rentACar.business.requests.createRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {

    @Positive
    @NotNull
    private double dailyPrice;

    @Min(1900)
    @NotNull
    private int modelYear;

    private String description;

    @NotNull
    private int brandId;
    @NotNull
    private int colorId;
}
