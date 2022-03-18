package com.turkcell.rentACar.business.requests.updateRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {

    private int id;

    private String invoiceNumber;

    private LocalDate invoiceDate;

    private int customerId;

    private int rentalId;
}
