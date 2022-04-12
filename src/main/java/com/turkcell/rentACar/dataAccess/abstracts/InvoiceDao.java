package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.Invoice;

import java.util.List;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer>{

    List<Invoice> findAllByRental_RentalId(int rentalId);

    Invoice findInvoiceByInvoiceNumber(String invoiceNumber);

}