package com.turkcell.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private int carId;

    @Column(name = "daily_price")
    private double dailyPrice;

    @Column(name = "model_year")
    private int modelYear;

    @Column(name = "description")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "kilometer_information")
    private double kilometerInformation;

    @ManyToOne()
    @JoinColumn(name = "color_id")
    private Color color;

    @OneToMany(mappedBy = "car")
	private List<CarMaintenance> carMaintenances;

    @OneToMany(mappedBy = "car")
    private List<Rental> rentals;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CarDamage> carDamages;

}
