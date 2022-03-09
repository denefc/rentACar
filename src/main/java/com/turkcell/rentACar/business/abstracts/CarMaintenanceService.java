package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.CarMaintenanceDto;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.requests.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface CarMaintenanceService {
    DataResult<List<CarMaintenanceListDto>> getAll();
    Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;
    Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;
    DataResult<CarMaintenanceDto> getById(int carMaintenanceId) throws BusinessException;
    Result deleteById(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException;
    DataResult<List<CarMaintenanceListDto>> getByCarId(int carId) throws BusinessException;
}
