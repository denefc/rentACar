package com.turkcell.rentACar.business.adapters.posAdapters;

import com.turkcell.rentACar.business.abstracts.PosService;
import com.turkcell.rentACar.business.dtos.PosDto;
import com.turkcell.rentACar.business.outServices.IsbankManager;
import com.turkcell.rentACar.business.outServices.ZiraatBankManager;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import org.springframework.stereotype.Service;

@Service
public class IsbankPosAdapter implements PosService {
    @Override
    public Result pos(PosDto posDto) throws BusinessException {
        IsbankManager isbankManager=new IsbankManager();
        boolean result=isbankManager.makePayment(posDto.getCardHolderName(), posDto.getCardNo(),posDto.getCvv());
        if(result){
            return  new SuccessResult();
        }
        return new ErrorResult();
    }
}
