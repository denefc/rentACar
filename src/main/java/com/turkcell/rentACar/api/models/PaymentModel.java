package com.turkcell.rentACar.api.models;


import com.turkcell.rentACar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateRentalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {

    private CreateRentalRequest createRentalRequest;
    private CreateInvoiceRequest createInvoiceRequest;
    private CreatePaymentRequest createPaymentRequest;



}
