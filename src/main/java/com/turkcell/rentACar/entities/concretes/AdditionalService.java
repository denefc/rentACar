package com.turkcell.rentACar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "additional_services")
public class AdditionalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "additional_service_id")
    private int additionalServiceId;

    @Column(name = "additional_service_name")
    private String name;

    @Column(name = "additional_service_dailyPrice")
    private double dailyPrice;

    //amount konulabilir...

    @OneToMany(mappedBy = "additionalService")
    private List<OrderedAdditionalService> orderedAdditionalServices;
}
