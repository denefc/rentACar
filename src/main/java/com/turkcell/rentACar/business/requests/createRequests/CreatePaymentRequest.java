package com.turkcell.rentACar.business.requests.createRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {


    private String cardNo;


    private String cardHolder;


    private int expirationMonth;


    private int expirationYear;


    private int cvv;
}
