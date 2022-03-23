package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.abstracts.CustomerCardInformationService;
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
public class CreateCustomerCardInformationManager implements CustomerCardInformationService {

    private final CustomerCardInformationDao customerCardInformationDao;
    private final ModelMapperService modelMapperService;
    private final CorporateCustomerService corporateCustomerService;

    @Autowired
    public CreateCustomerCardInformationManager(CustomerCardInformationDao customerCardInformationDao, ModelMapperService modelMapperService, CorporateCustomerService corporateCustomerService) {
        this.customerCardInformationDao = customerCardInformationDao;
        this.modelMapperService = modelMapperService;

        this.corporateCustomerService = corporateCustomerService;
    }

    @Override
    public DataResult<List<CustomerCardInformationListDto>> getAll() {
        List<CustomerCardInformation> result = this.customerCardInformationDao.findAll();
        List<CustomerCardInformationListDto> response = result.stream()
                .map(customerCardInformation->this.modelMapperService.forDto().map(customerCardInformation,CustomerCardInformationListDto.class)).collect(Collectors.toList());


        return new SuccessDataResult<>(response,"customerCard listed");
    }

    @Override
    public Result add(CreateCustomerCardInformationRequest createCustomerCardInformationRequest) throws BusinessException {
        CustomerCardInformation customerCardInformation = this.modelMapperService.forRequest().map(createCustomerCardInformationRequest, CustomerCardInformation.class);
        //burda customerService.checkCustomerId gelecek
        if(null==corporateCustomerService.getCustomerById(createCustomerCardInformationRequest.getCustomerId())){
            throw  new BusinessException(BusinessMessages.ERROR_CUSTOMER);
        }

        return new SuccessResult("CustomerCard is added.");
    }

    @Override
    public DataResult<CustomerCardInformationDto> getById(int id) throws BusinessException {
        checkIfCustomerCardInformation(id);
        CustomerCardInformation customerCardInformation = this.customerCardInformationDao.getById(id);

        CustomerCardInformationDto corporateCustomerDto = this.modelMapperService.forDto().map(customerCardInformation,CustomerCardInformationDto.class);

        return new SuccessDataResult<>(corporateCustomerDto,"Data get Successfully.");
    }



    @Override
    public Result update(UpdateCustomerCardInformationRequest updateCustomerCardInformationRequest) throws BusinessException {
        //burda customerService.checkCustomerId gelecek
        CustomerCardInformation customerCardInformation  = this.modelMapperService.forRequest().map(updateCustomerCardInformationRequest,
                CustomerCardInformation.class);
        customerCardInformationDao.save(customerCardInformation);
        return new SuccessResult("CustomerCardInfo updated successfully");
    }

    @Override
    public Result deleteById(int id) throws BusinessException {
        checkIfCustomerCardInformation(id);
        this.customerCardInformationDao.deleteById(id);
        return new SuccessResult("CustomerCardInfo deleted successfully");
    }

    private void checkIfCustomerCardInformation(int id)throws BusinessException {
        if(!customerCardInformationDao.existsById(id)){
            throw new BusinessException(BusinessMessages.ERROR_CUSTOMER);
        }
    }


}
