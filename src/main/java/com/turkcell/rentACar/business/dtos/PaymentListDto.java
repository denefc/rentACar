package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentListDto {

    private int paymentId;

    private String cardNo;

    private String month;

    private String year;

    private String cvv;

    private LocalDate paymentDate;

    private double paymentTotal;
}