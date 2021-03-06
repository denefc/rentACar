package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceListDto {

    private int orderedAdditionalServiceId;
    private int additionalServiceId;

    private String additionalServiceName;

    private double additionalServiceDailyPrice;

    private int rentalId;
}
