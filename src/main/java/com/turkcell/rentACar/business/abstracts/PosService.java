package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.PosDto;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface PosService {
    Result pos(PosDto posDto) throws BusinessException;
}
