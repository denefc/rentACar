package com.turkcell.rentACar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="customer_card_informations")
public class CustomerCardInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_card_information_id")
    private int customerCardInformationId;

    @Column(name = "card_no")
    private String cardNo;

    @Column(name = "card_holder")
    private String cardHolder;

    @Column(name = "expire_month")
    private String expirationMonth;

    @Column(name = "expire_year")
    private String expirationYear;

    @Column(name = "cvv")
    private String cvv;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
