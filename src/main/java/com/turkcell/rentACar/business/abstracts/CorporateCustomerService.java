package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.CorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCorporateCustomerRequest;

import com.turkcell.rentACar.business.requests.updateRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.CorporateCustomer;

import java.util.List;

public interface CorporateCustomerService {
    DataResult<List<CorporateCustomerListDto>> getAll();

    Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;

    DataResult<CorporateCustomerDto> getById(int id) throws BusinessException;

    Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException;

    Result delete(int id) throws BusinessException;

    CorporateCustomer getCustomerById(int id);

    void checkIfCorporateCustomerExists(int id) throws BusinessException;
}
