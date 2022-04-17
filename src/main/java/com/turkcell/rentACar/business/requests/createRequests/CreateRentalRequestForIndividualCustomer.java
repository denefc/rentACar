package com.turkcell.rentACar.business.requests.createRequests;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequestForIndividualCustomer {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @Nullable
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    @NotNull
    @Positive
    private int car_CarId;

    @NotNull
    @Positive
    private int individualCustomerId;

    @NotNull
    private int cityOfPickUpLocation_CityId;

    @NotNull
    private int cityOfReturnLocation_CityId;

    @Nullable
    private List<Integer> additionalServicesId;
}
