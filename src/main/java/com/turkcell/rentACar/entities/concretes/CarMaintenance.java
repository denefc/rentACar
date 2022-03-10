package com.turkcell.rentACar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "car_maintenances")
public class CarMaintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_maintenance_id")
    private int carMaintenanceId;

    @Column(name = "description")
    private String description;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @ManyToOne()
    @JoinColumn(name="car_id")
    private Car car;
}