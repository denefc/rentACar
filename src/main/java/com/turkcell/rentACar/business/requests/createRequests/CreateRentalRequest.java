package com.turkcell.rentACar.business.requests.createRequests;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.format.annotation.DateTimeFormat;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @Nullable
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    @NotNull
    @Min(1)
    private int cityOfPickUpLocationId;

    @NotNull
    @Min(1)
    private int cityOfReturnLocationId;

    @Min(0)
    private double returnKilometer;

    @NotNull
    @Min(1)
    private int carCarId;

    @NotNull
    @Min(1)
    private int customerId;




}
