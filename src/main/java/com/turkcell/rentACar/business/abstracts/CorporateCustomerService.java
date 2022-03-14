package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.CorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.dtos.UserDto;
import com.turkcell.rentACar.business.dtos.UserListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateUserRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateCorporateCustomer;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateUserRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface CorporateCustomerService {
    DataResult<List<CorporateCustomerListDto>> getAll();

    Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;

    DataResult<CorporateCustomerDto> getById(int id) throws BusinessException;

    Result update(UpdateCorporateCustomer updateCorporateCustomer) throws BusinessException;

    Result deleteById(int id) throws BusinessException;

}
