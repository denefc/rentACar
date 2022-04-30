package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.api.models.CreateCorporatePaymentModel;
import com.turkcell.rentACar.api.models.CreateIndividualPaymentModel;
import com.turkcell.rentACar.api.models.DelayedPaymentModel;
import com.turkcell.rentACar.business.dtos.PaymentDto;
import com.turkcell.rentACar.business.dtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.updateRequests.UpdatePaymentRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface PaymentService {

    DataResult<List<PaymentListDto>> getAll() throws BusinessException;

    Result paymentForIndividualCustomer(CreateIndividualPaymentModel createIndividualPaymentModel) throws BusinessException;

    Result paymentForCorporateCustomer(CreateCorporatePaymentModel createCorporatePaymentModel) throws BusinessException;

    Result additionalPaymentForDelaying(DelayedPaymentModel delayedPaymentModel) throws BusinessException;

    DataResult<PaymentDto> getById(int id) throws BusinessException;


    Result delete(int id) throws BusinessException;
}
