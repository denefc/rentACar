package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.entities.concretes.Customer;

public interface CustomerService {

    Customer getCustomerById(int customerId) throws BusinessException;

    void checkIfCustomerExists(int customerId) throws BusinessException;
}
