package com.turkcell.rentACar.business.adapters.posAdapters;

import com.turkcell.rentACar.business.abstracts.PosService;
import com.turkcell.rentACar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentACar.business.outServices.IsbankManager;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import org.springframework.stereotype.Service;

@Service
public class IsbankPosAdapter implements PosService {
    @Override
    public Result makePayment(CreatePaymentRequest createPaymentRequest) throws BusinessException {
        IsbankManager isbankManager=new IsbankManager();
        boolean result=isbankManager.makePayment(createPaymentRequest.getCardNo(), createPaymentRequest.getCardHolder(), createPaymentRequest.getExpirationMonth(), createPaymentRequest.getExpirationYear(), createPaymentRequest.getCvv());
        if(result){
            return  new SuccessResult();
        }
        return new ErrorResult();
    }
}
