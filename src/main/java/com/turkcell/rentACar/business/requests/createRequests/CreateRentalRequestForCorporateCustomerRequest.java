package com.turkcell.rentACar.business.requests.createRequests;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequestForCorporateCustomerRequest {

    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    @Positive
    private int car_CarId;

    @NotNull
    @Positive
    private int corporateCustomerId;

    @NotNull
    @Positive
    private int pickUpLocationIdCityId;

    @NotNull
    @Positive
    private int returnLocationIdCityId;

    private List<Integer> additionalServicesId;
}
