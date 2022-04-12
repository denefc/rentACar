package com.turkcell.rentACar.entities.concretes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="invoice_id")
    private int invoiceId;

    @Column(name="invoice_number")
    private String invoiceNumber;

    @Column(name="invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "additional_service_total_payment")
    private double additionalServiceTotalPayment;

    @Column(name = "rent_day")
    private int rentDay;

    @Column(name = "rent_payment")
    private double rentPayment;

    @Column(name = "rent_location_payment")
    private double rentLocationPayment;

    @Column(name="total_payment")
    private double totalPayment;

    @ManyToOne()
    @JoinColumn(name = "rental_id")
    private Rental rental;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
