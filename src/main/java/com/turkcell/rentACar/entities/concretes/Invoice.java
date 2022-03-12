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

    @Column(name="total_payment")
    private LocalDate totalPayment;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private List<Rental> rentals;

}
