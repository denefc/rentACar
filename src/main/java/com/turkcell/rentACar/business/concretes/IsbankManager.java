package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.IsbankPosService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.PosDto;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import org.springframework.stereotype.Service;

@Service
public class IsbankManager implements IsbankPosService {
    @Override
    public Result pos(PosDto posDto) throws BusinessException {
        checkIfExistsPos(posDto);
        return new SuccessResult();
    }

    private boolean checkIfExistsPos(PosDto posDto) throws BusinessException {
        boolean exists = true;
        if (!exists) {
            throw new BusinessException("Pos doesnt exis");
        }
        return exists;
    }
}
