package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.ColorDto;
import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateColorRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateColorRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface ColorService {

    DataResult<List<ColorListDto>> getAll();

    Result add(CreateColorRequest createColorRequest) throws BusinessException;

    DataResult<ColorDto> getById(int id) throws BusinessException;

    Result update(UpdateColorRequest updateColorRequest) throws BusinessException;

    Result deleteById(int id) throws BusinessException;

    void checkIfExists(int id) throws BusinessException;
}
