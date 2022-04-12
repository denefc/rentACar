package com.turkcell.rentACar.api.models;


import com.turkcell.rentACar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateRentalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {
    @Valid
    private CreateRentalRequest createRentalRequest;

    @Valid
    private CreatePaymentRequest createPaymentRequest;
}
