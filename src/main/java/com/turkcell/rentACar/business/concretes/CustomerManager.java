package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentACar.entities.concretes.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerManager implements CustomerService {
    private final CustomerDao customerDao;

    @Autowired
    public CustomerManager(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Customer getCustomerById(int customerId) throws BusinessException {
        checkIfCustomerExists(customerId);
        return customerDao.getById(customerId);
    }

    @Override
    public void checkIfCustomerExists(int customerId) throws BusinessException {
        if (!customerDao.existsById(customerId)){
            throw new BusinessException(BusinessMessages.CUSTOMER_NOT_FOUND+customerId);
        }
    }


}
