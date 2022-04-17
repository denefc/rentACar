package com.turkcell.rentACar.api.models;

import com.turkcell.rentACar.business.requests.createRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreatePaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCorporatePaymentModel {
    @Valid
    private CreateCorporateCustomerRequest createCorporateCustomerRequest;

    @Valid
    private CreatePaymentRequest createPaymentRequest;
}
