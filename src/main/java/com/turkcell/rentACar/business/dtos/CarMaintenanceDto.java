package com.turkcell.rentACar.business.dtos;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceDto {

	private int carMaintenanceId;

	private String description;

	private LocalDate returnDate;

	private int carCarId;


	
}
