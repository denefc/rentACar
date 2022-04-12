package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CustomerCardInformationService;
import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CustomerCardInformationDto;
import com.turkcell.rentACar.business.dtos.CustomerCardInformationListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCustomerCardInformationRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateCustomerCardInformationRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CustomerCardInformationDao;
import com.turkcell.rentACar.entities.concretes.CustomerCardInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerCardInformationManager implements CustomerCardInformationService {

    private final CustomerCardInformationDao customerCardInformationDao;
    private final ModelMapperService modelMapperService;
    private final CustomerService customerService;

    @Autowired
    public CustomerCardInformationManager(CustomerCardInformationDao customerCardInformationDao, ModelMapperService modelMapperService, CustomerService customerService) {
        this.customerCardInformationDao = customerCardInformationDao;
        this.modelMapperService = modelMapperService;
        this.customerService = customerService;
    }

    @Override
    public DataResult<List<CustomerCardInformationListDto>> getAll() {
        List<CustomerCardInformation> result = this.customerCardInformationDao.findAll();
        List<CustomerCardInformationListDto> response = result.stream()
                .map(customerCardInformation->this.modelMapperService.forDto().map(customerCardInformation,CustomerCardInformationListDto.class)).collect(Collectors.toList());


        return new SuccessDataResult<>(response,BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateCustomerCardInformationRequest createCustomerCardInformationRequest) throws BusinessException {
        customerService.checkIfCustomerExists(createCustomerCardInformationRequest.getCustomerId());
        CustomerCardInformation customerCardInformation = this.modelMapperService.forRequest().map(createCustomerCardInformationRequest, CustomerCardInformation.class);

        customerCardInformation.setCustomer(customerService.getCustomerById(createCustomerCardInformationRequest.getCustomerId()));

        customerCardInformationDao.save(customerCardInformation);

        return new SuccessResult(BusinessMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public DataResult<CustomerCardInformationDto> getById(int id) throws BusinessException {
        checkIfCustomerCardInformationExists(id);
        CustomerCardInformation customerCardInformation = this.customerCardInformationDao.getById(id);

        CustomerCardInformationDto corporateCustomerDto = this.modelMapperService.forDto().map(customerCardInformation,CustomerCardInformationDto.class);

        return new SuccessDataResult<>(corporateCustomerDto,BusinessMessages.DATA_GET_SUCCESSFULLY);
    }



    @Override
    public Result update(UpdateCustomerCardInformationRequest updateCustomerCardInformationRequest) throws BusinessException {
        customerService.checkIfCustomerExists(updateCustomerCardInformationRequest.getCustomerId());
        checkIfCardNoAlreadyExists(updateCustomerCardInformationRequest.getCardNo());
        CustomerCardInformation customerCardInformation  = this.modelMapperService.forRequest().map(updateCustomerCardInformationRequest,
                CustomerCardInformation.class);
        customerCardInformationDao.save(customerCardInformation);
        return new SuccessResult(BusinessMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public Result deleteById(int id) throws BusinessException {
        checkIfCustomerCardInformationExists(id);
        this.customerCardInformationDao.deleteById(id);
        return new SuccessResult(BusinessMessages.DATA_DELETED_SUCCESSFULLY);
    }

    private void checkIfCustomerCardInformationExists(int id)throws BusinessException {
        if(!customerCardInformationDao.existsById(id)){
            throw new BusinessException(BusinessMessages.CARD_NO_ALREADY_EXISTS);
        }
    }

    private void checkIfCardNoAlreadyExists(String cardNo) throws BusinessException {
        if(customerCardInformationDao.existsByCardNo(cardNo)){
            throw new BusinessException(BusinessMessages.CARD_NO_ALREADY_EXISTS);
        }
    }



}
