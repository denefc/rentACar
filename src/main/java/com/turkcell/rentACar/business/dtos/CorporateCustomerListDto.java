package com.turkcell.rentACar.business.dtos;

import com.turkcell.rentACar.entities.concretes.Invoice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateCustomerListDto {
    private int corporateId;

    private String email;

    private String password;

    private String companyName;

    private String taxNumber;

    private List<Invoice> invoices;
}
