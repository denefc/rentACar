package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private String cardNo;


    private String cardHolder;


    private int expirationMonth;


    private int expirationYear;


    private int cvv;


    private double paymentTotal;
}

