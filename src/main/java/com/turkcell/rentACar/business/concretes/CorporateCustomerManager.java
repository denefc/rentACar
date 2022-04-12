package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCorporateCustomerRequest;

import com.turkcell.rentACar.business.requests.updateRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentACar.entities.concretes.CorporateCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

    private final CorporateCustomerDao corporateCustomerDao;
    private final ModelMapperService modelMapperService;
    private final UserService userService;

    @Autowired
    public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService, UserService userService) {
        this.corporateCustomerDao = corporateCustomerDao;
        this.modelMapperService = modelMapperService;
        this.userService = userService;
    }

    @Override
    public DataResult<List<CorporateCustomerListDto>> getAll() {
        List<CorporateCustomer> result = this.corporateCustomerDao.findAll();
        List<CorporateCustomerListDto> response = result.stream().map(corporateCustomer -> this.modelMapperService
                .forDto().map(corporateCustomer, CorporateCustomerListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(response, BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
        checkIfCompanyNameExists(createCorporateCustomerRequest.getCompanyName());
        checkIfTaxNumberExists(createCorporateCustomerRequest.getTaxNumber());
        userService.checkIfUserAlreadyExistsByEmail(createCorporateCustomerRequest.getEmail());
        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
        corporateCustomer.setDateRegistered(LocalDate.now());
        corporateCustomer.setUserId(0);

        this.corporateCustomerDao.save(corporateCustomer);

        return new SuccessResult(BusinessMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public DataResult<CorporateCustomerDto> getById(int id) throws BusinessException {
        checkIfCorporateCustomerExists(id);
        CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(id);

        CorporateCustomerDto corporateCustomerDto = this.modelMapperService.forDto().map(corporateCustomer,CorporateCustomerDto.class);

        return new SuccessDataResult<>(corporateCustomerDto,BusinessMessages.DATA_GET_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
        checkIfCorporateCustomerExists(updateCorporateCustomerRequest.getId());
        userService.checkIfUserAlreadyExistsByEmail(updateCorporateCustomerRequest.getEmail());
        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,
                CorporateCustomer.class);
        corporateCustomerDao.save(corporateCustomer);

        return new SuccessResult(BusinessMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public Result delete(int id) throws BusinessException {
        checkIfCorporateCustomerExists(id);
        this.corporateCustomerDao.deleteById(id);
        return new SuccessResult(BusinessMessages.DATA_DELETED_SUCCESSFULLY);
    }

    @Override
    public CorporateCustomer getCustomerById(int id) {
        return this.corporateCustomerDao.getById(id);
    }

    @Override
    public void checkIfCorporateCustomerExists(int id) throws BusinessException {
        if (!corporateCustomerDao.existsById(id)){
            throw new BusinessException(BusinessMessages.CORPORATE_CUSTOMER_NOT_FOUND);
        }
    }

    private void checkIfCompanyNameExists(String name)throws BusinessException{
       if(corporateCustomerDao.existsByCompanyName(name)){
           throw new BusinessException(BusinessMessages.CORPORATE_COMPANY_NAME_ALREADY_EXISTS+name);
       }

    }

    private void checkIfTaxNumberExists(String taxNumber)throws BusinessException{
        if(corporateCustomerDao.existsCorporateCustomerByTaxNumber(taxNumber)){
            throw new BusinessException(BusinessMessages.CORPORATE_CUSTOMER_TAX_NUMBER_ALREADY_EXISTS+taxNumber);
        }
    }

}
