package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.api.models.CreateCorporatePaymentModel;
import com.turkcell.rentACar.api.models.CreateIndividualPaymentModel;
import com.turkcell.rentACar.api.models.DelayedPaymentModel;
import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.requests.createRequests.*;
import com.turkcell.rentACar.business.dtos.PaymentDto;
import com.turkcell.rentACar.business.dtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.updateRequests.UpdatePaymentRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACar.entities.concretes.Invoice;
import com.turkcell.rentACar.entities.concretes.Payment;
import com.turkcell.rentACar.entities.concretes.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentManager implements PaymentService {
    private final PaymentDao paymentDao;
    private final ModelMapperService modelMapperService;
    private final PosService posService;
    private final InvoiceService invoiceService;
    private final RentalService rentalService;
    private final OrderedAdditionalServiceService orderedAdditionalServiceService;
    private final CustomerService customerService;

    @Autowired
    public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, PosService posService, InvoiceService invoiceService, RentalService rentalService, OrderedAdditionalServiceService orderedAdditionalServiceService, CustomerService customerService) {
        this.paymentDao = paymentDao;
        this.modelMapperService = modelMapperService;
        this.posService = posService;
        this.invoiceService = invoiceService;
        this.rentalService = rentalService;
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
        this.customerService = customerService;
    }

    @Override
    public DataResult<List<PaymentListDto>> getAll() throws BusinessException {
        List<Payment> payments = paymentDao.findAll();
        List<PaymentListDto> paymentListDtos = payments.stream()
                .map(payment -> modelMapperService.forDto().map(payment, PaymentListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(paymentListDtos, BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = BusinessException.class)
    @Override
    public Result paymentForIndividualCustomer(CreateIndividualPaymentModel createIndividualPaymentModel) throws BusinessException {
        posService.makePayment(createIndividualPaymentModel.getCreatePaymentRequest());
        checkIfPaymentSuccess(createIndividualPaymentModel.getCreatePaymentRequest());
        runPaymentSuccessorForIndividual(createIndividualPaymentModel);
        return new SuccessResult(BusinessMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public Result paymentForCorporateCustomer(CreateCorporatePaymentModel createCorporatePaymentModel) throws BusinessException {
        posService.makePayment(createCorporatePaymentModel.getCreatePaymentRequest());
        checkIfPaymentSuccess(createCorporatePaymentModel.getCreatePaymentRequest());
        runPaymentSuccessorForCorporate(createCorporatePaymentModel);
        return new SuccessResult(BusinessMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
    @Override
    public Result additionalPaymentForDelaying(DelayedPaymentModel delayedPaymentModel) throws BusinessException {
        rentalService.checkIfRentalExists(delayedPaymentModel.getRentalId());
        checkIfPaymentSuccess(delayedPaymentModel.getCreatePaymentRequest());

        return new SuccessResult(BusinessMessages.DATA_ADDED_SUCCESSFULLY);
    }


    @Override
    public DataResult<PaymentDto> getById(int id) throws BusinessException {

        checkIfPaymentExists(id);

        Payment payment = this.paymentDao.getById(id);

        PaymentDto paymentDto = this.modelMapperService.forDto().map(payment, PaymentDto.class);

        return new SuccessDataResult<>(paymentDto);

    }



    @Override
    public Result delete(int id) throws BusinessException {
        this.paymentDao.deleteById(id);

        return new SuccessResult(BusinessMessages.DATA_DELETED_SUCCESSFULLY);
    }



    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = BusinessException.class)
    public void runPaymentSuccessorForIndividual(CreateIndividualPaymentModel createIndividualPaymentModel) throws BusinessException {
        Rental rental= getRentalForIndividual(createIndividualPaymentModel.getCreateRentalRequestForIndividualCustomerRequest());
        CreateInvoiceRequest createInvoiceRequest=new CreateInvoiceRequest(rental.getRentalId());
        this.orderedAdditionalServiceService.orderAdditionalServices(createIndividualPaymentModel.getCreateRentalRequestForIndividualCustomerRequest().getAdditionalServicesId(),rental.getRentalId());
        rental.setOrderedAdditionalServices(orderedAdditionalServiceService.getOrderedAdditionalServicesByRentalId(rental.getRentalId()));
        Invoice invoice = this.invoiceService.add(createInvoiceRequest);

        Payment payment = this.modelMapperService.forRequest().map(createIndividualPaymentModel.getCreatePaymentRequest(), Payment.class);
        payment.setCustomer(customerService.getCustomerById(createIndividualPaymentModel.getCreateRentalRequestForIndividualCustomerRequest().getIndividualCustomerId()));
        payment.setPaymentAmount(invoice.getTotalPayment());

        this.paymentDao.save(payment);

    }
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = BusinessException.class)
    public void runPaymentSuccessorForCorporate(CreateCorporatePaymentModel createCorporatePaymentModel) throws BusinessException {
        Rental rental= getRentalForCorporate(createCorporatePaymentModel.getCreateRentalRequestForCorporateCustomerRequest());
        CreateInvoiceRequest createInvoiceRequest=new CreateInvoiceRequest(rental.getRentalId());
        this.orderedAdditionalServiceService.orderAdditionalServices(createCorporatePaymentModel.getCreateRentalRequestForCorporateCustomerRequest().getAdditionalServicesId(),rental.getRentalId());
        rental.setOrderedAdditionalServices(orderedAdditionalServiceService.getOrderedAdditionalServicesByRentalId(rental.getRentalId()));
        Invoice invoice = this.invoiceService.add(createInvoiceRequest);

        Payment payment = this.modelMapperService.forRequest().map(createCorporatePaymentModel.getCreatePaymentRequest(), Payment.class);
        payment.setCustomer(customerService.getCustomerById(createCorporatePaymentModel.getCreateRentalRequestForCorporateCustomerRequest().getCorporateCustomerId()));
        payment.setPaymentAmount(invoice.getTotalPayment());

        this.paymentDao.save(payment);

    }

    private Rental getRentalForIndividual(CreateRentalRequestForIndividualCustomer createRentalRequestForIndividualCustomerRequest) throws BusinessException {
        return this.rentalService.addForIndividualCustomer(createRentalRequestForIndividualCustomerRequest).getData();
    }

    private Rental getRentalForCorporate(CreateRentalRequestForCorporateCustomerRequest createRentalRequestForCorporateCustomerRequest) throws BusinessException {
        return this.rentalService.addForCorporateCustomer(createRentalRequestForCorporateCustomerRequest).getData();
    }

    private void checkIfPaymentExists(int id)throws BusinessException{
        if (!this.paymentDao.existsById(id)) {
            throw new BusinessException(BusinessMessages.PAYMENT_NOT_FOUND );
        }
    }
    private void checkIfPaymentSuccess(CreatePaymentRequest createPaymentRequest)throws BusinessException{
        if (!posService.makePayment(createPaymentRequest).isSuccess()){
            throw new BusinessException(BusinessMessages.INVALID_PAYMENT);
        }
    }


}
