package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.CityDto;
import com.turkcell.rentACar.business.dtos.CityListDto;
import com.turkcell.rentACar.business.dtos.CustomerCardInformationDto;
import com.turkcell.rentACar.business.dtos.CustomerCardInformationListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCityRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateCustomerCardInformationRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateCityRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateCustomerCardInformationRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface CustomerCardInformationService {

    DataResult<List<CustomerCardInformationListDto>> getAll();

    Result add(CreateCustomerCardInformationRequest createCustomerCardInformationRequest) throws BusinessException;

    DataResult<CustomerCardInformationDto> getById(int id) throws BusinessException;

    Result update(UpdateCustomerCardInformationRequest updateCustomerCardInformationRequest) throws BusinessException;

    Result deleteById(int id) throws BusinessException;
}
