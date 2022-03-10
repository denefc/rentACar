package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.dtos.AdditionalServicesDto;
import com.turkcell.rentACar.business.dtos.CarDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateCarRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface AdditionalServiceService {

    DataResult<List<AdditionalServiceListDto>> getAll();

    Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest)throws BusinessException;

    Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException;

    DataResult<AdditionalServicesDto> getById(int id) throws BusinessException;

    Result deleteById(int id) throws BusinessException;
}
