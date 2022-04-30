package com.turkcell.rentACar.api.models;

import com.turkcell.rentACar.business.requests.createRequests.CreateRentalRequestForCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateRentalRequestForCorporateCustomerRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCorporatePaymentModel {
    @Valid
    private CreateRentalRequestForCorporateCustomerRequest createRentalRequestForCorporateCustomerRequest;

    @Valid
    private CreatePaymentRequest createPaymentRequest;
}
