package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceListDto {

    private int invoiceId;

    private int invoiceNumber;

    private LocalDate invoiceDate;

    private String customerName;

    private double totalPayment;

    private RentalListDto rentalListDto;
}