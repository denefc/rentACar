package com.turkcell.rentACar.business.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {
    @NotNull
    @Positive
    private int rentalId;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    @Positive
    private int carCarId;
}
