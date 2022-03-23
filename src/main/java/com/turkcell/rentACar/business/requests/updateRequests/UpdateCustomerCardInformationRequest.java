package com.turkcell.rentACar.business.requests.updateRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerCardInformationRequest {

    private String cardNo;

    private String month;

    private String year;

    private String cvv;

    private int customerId;
}
