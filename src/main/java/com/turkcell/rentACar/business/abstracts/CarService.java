package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.CarDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCarRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CarService {

    DataResult<List<CarListDto>> getAll();

    Result add(CreateCarRequest createCarRequest);

    Result update(UpdateCarRequest createCarRequest) throws BusinessException;

    DataResult<CarDto> getById(int carId) throws BusinessException;

    Result deleteById(int id) throws BusinessException;

    DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize);

    DataResult<List<CarListDto>> getAllSorted(String ascOrDesc) throws BusinessException ;

    DataResult<List<CarListDto>> getByDailyPriceIsLessThanEqual(double dailyPrice);

    DataResult<List<CarListDto>> getByModelYearIsLessThanEqual(int modelYear);

    void checkIfCarExists(int id) throws BusinessException;
}
