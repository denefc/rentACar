package com.turkcell.rentACar.business.adapters.posAdapters;

import com.turkcell.rentACar.business.abstracts.PosService;
import com.turkcell.rentACar.business.dtos.PosDto;
import com.turkcell.rentACar.business.outServices.ZiraatBankManager;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ZiraatBankPosAdapter implements PosService {
    @Override
    public Result pos(PosDto posDto) throws BusinessException {
        ZiraatBankManager ziraatBankManager=new ZiraatBankManager();
        boolean result=ziraatBankManager.makePayment(posDto.getCardHolderName(), posDto.getCardNo(),posDto.getCvv());
       if(result){
           return  new SuccessResult();
       }
       return new ErrorResult();
    }
}
