package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.PaymentDto;
import com.turkcell.rentACar.business.dtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdatePaymentRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.Rental;

import java.util.List;

public interface PaymentService {

    DataResult<List<PaymentListDto>> getAll() throws BusinessException;

    Result add(CreatePaymentRequest createPaymentRequest) throws BusinessException;

    DataResult<PaymentDto> getById(int id) throws BusinessException;

    Result update(UpdatePaymentRequest updatePaymentRequest) throws BusinessException;

    Result delete(int id) throws BusinessException;
}
