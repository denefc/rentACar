package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.CityDto;
import com.turkcell.rentACar.business.dtos.CityListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCityRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateCityRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface CityService {
    DataResult<List<CityListDto>> getAll();

    Result add(CreateCityRequest createCityRequest) throws BusinessException;

    DataResult<CityDto> getById(int id) throws BusinessException;

    Result update(UpdateCityRequest updateCityRequest) throws BusinessException;

    Result deleteById(int id) throws BusinessException;
}
