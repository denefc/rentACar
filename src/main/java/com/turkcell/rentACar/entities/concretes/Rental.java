package com.turkcell.rentACar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private int rentalId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToMany(mappedBy = "rental",cascade = CascadeType.ALL)
    private List<OrderedAdditionalService> orderedAdditionalServices;

    @ManyToOne
    @JoinColumn(name="city_pick_up_id")
    private City cityOfPickUpLocation;

    @ManyToOne
    @JoinColumn(name="city_return_id")
    private City cityOfReturnLocation;

    @Column(name = "rent_start_kilometer")
    private double rentStartKilometer;

    @Column(name = "return_kilometer")
    private double returnKilometer;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;


}
