package com.turkcell.rentACar.business.requests.createRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerCardInformationRequest {
    private String cardNo;

    private String month;

    @NotNull
    private String year;

    private String cvv;

    private int customerId;
}
