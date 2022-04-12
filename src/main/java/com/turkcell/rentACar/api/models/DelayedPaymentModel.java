package com.turkcell.rentACar.api.models;

import com.turkcell.rentACar.business.requests.createRequests.CreatePaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DelayedPaymentModel {

    private int rentalId;

    private LocalDate delayedReturnDate;

    @Min(0)
    private double carDelayedKilometerInformation;

    @Valid
    private CreatePaymentRequest createPaymentRequest;

}
