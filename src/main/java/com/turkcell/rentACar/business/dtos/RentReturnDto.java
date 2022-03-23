package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentReturnDto {
    private int delayDays;
    private double extraRentPayments;
    private double extraAdditionalServicePayments;

}
