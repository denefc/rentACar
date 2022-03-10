package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.RentalDto;
import com.turkcell.rentACar.business.dtos.RentalListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateRentalRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface RentalService {
    DataResult<List<RentalListDto>> getAll();

    DataResult<List<RentalListDto>> getAllByCarCarId(int id) throws BusinessException;

    Result add(CreateRentalRequest createRentalRequest) throws BusinessException;

    DataResult<RentalDto> getById(int id) throws BusinessException;

    Result update(UpdateRentalRequest updateRentalRequest) throws BusinessException;

    Result deleteById(int id) throws BusinessException;
}
