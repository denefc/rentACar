package com.turkcell.rentACar.api.models;


import com.turkcell.rentACar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateRentalRequestForIndividualCustomer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualPaymentModel {
    @Valid
    private CreateRentalRequestForIndividualCustomer createRentalRequestForIndividualCustomerRequest;

    @Valid
    private CreatePaymentRequest createPaymentRequest;

}
