package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.PaymentDto;
import com.turkcell.rentACar.business.dtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdatePaymentRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACar.entities.concretes.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentManager implements PaymentService {
    private final PaymentDao paymentDao;
    private final ModelMapperService modelMapperService;
    private final PosService posService;
    private final InvoiceService invoiceService;

    @Autowired
    public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, PosService posService, InvoiceService invoiceService) {
        this.paymentDao = paymentDao;
        this.modelMapperService = modelMapperService;
        this.posService = posService;

        this.invoiceService = invoiceService;
    }

    @Override
    public DataResult<List<PaymentListDto>> getAll() throws BusinessException {
        List<Payment> payments = paymentDao.findAll();
        List<PaymentListDto> paymentListDtos = payments.stream()
                .map(payment -> modelMapperService.forDto().map(payment, PaymentListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(paymentListDtos, BusinessMessages.PAYMENT_SUCCEED);
    }

    @Override
    public Result add(CreatePaymentRequest createPaymentRequest) throws BusinessException {

        Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest,Payment.class);
        payment.setPaymentDate(Date.from(Instant.now()));

        payment.setPaymentId(0);

        this.paymentDao.save(payment);

        return new SuccessResult();
    }

    @Override
    public DataResult<PaymentDto> getById(int id) throws BusinessException {
        return null;
    }

    @Override
    public Result update(UpdatePaymentRequest updatePaymentRequest) throws BusinessException {
        return null;
    }

    @Override
    public Result delete(int id) throws BusinessException {
        return null;
    }

    //hakanın vidyoyu izle
}
