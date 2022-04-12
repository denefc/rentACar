package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.CarDamageDto;
import com.turkcell.rentACar.business.dtos.CarListDamageDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface CarDamageService {

    DataResult<List<CarListDamageDto>> getAll();

    Result add(CreateCarDamageRequest createCarDamage) throws BusinessException;

    DataResult<CarDamageDto> getById(int id) throws BusinessException;

    Result delete(int id) throws BusinessException;

    Result update(UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException;
}
