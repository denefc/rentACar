package com.turkcell.rentACar.business.requests.updateRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCityRequest {
    @NotNull
    @Positive
    private int cityId;

    @NotNull
    private String cityName;
}
