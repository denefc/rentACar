package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PosDto {

    private String cardHolderName;

    private int cardNo;

    private LocalDate expirationDate;

    private int cvv;
}
