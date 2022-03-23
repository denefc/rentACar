package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCardInformationDto {
    private String cardNo;

    private String month;

    private String year;

    private String cvv;

    private CustomerDto customerDto;

}
