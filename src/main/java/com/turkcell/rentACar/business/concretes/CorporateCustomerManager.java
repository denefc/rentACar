package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
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

    @Autowired
    public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {
        this.corporateCustomerDao = corporateCustomerDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CorporateCustomerListDto>> getAll() {
        List<CorporateCustomer> result = this.corporateCustomerDao.findAll();
        List<CorporateCustomerListDto> response = result.stream().map(corporateCustomer -> this.modelMapperService
                .forDto().map(corporateCustomer, CorporateCustomerListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(response,"Corporate customers listed successfully");
    }

    @Override
    public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
        checkIfCompanyNameExists(createCorporateCustomerRequest.getCompanyName());
        checkIfTaxNumberExists(createCorporateCustomerRequest.getTaxNumber());
        corporateCustomer.setDateRegistered(LocalDate.now());

        corporateCustomer.setCustomerId(0);

        this.corporateCustomerDao.save(corporateCustomer);

        return new SuccessResult("Corporate customer is added.");
    }

    @Override
    public DataResult<CorporateCustomerDto> getById(int id) throws BusinessException {

        checkIfCorporateCustomer(id);
        CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(id);

        CorporateCustomerDto corporateCustomerDto = this.modelMapperService.forDto().map(corporateCustomer,CorporateCustomerDto.class);

        return new SuccessDataResult<>(corporateCustomerDto,"Data get Successfully.");
    }

    @Override
    public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
        checkIfCorporateCustomer(updateCorporateCustomerRequest.getId());
        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,
                CorporateCustomer.class);
        corporateCustomerDao.save(corporateCustomer);
        return new SuccessResult("Corporate customer updated successfully");

    }

    @Override
    public Result delete(int id) throws BusinessException {
        checkIfCorporateCustomer(id);
        this.corporateCustomerDao.deleteById(id);
        return new SuccessResult("Corporate customer deleted successfully.");
    }

    @Override
    public CorporateCustomer getCustomerById(int id) {
        return this.corporateCustomerDao.getById(id);
    }

    private void checkIfCompanyNameExists(String name)throws BusinessException{
       if(corporateCustomerDao.existsByCompanyName(name)){
           throw new BusinessException("This Company name already exist ");
       }

    }

    private void checkIfTaxNumberExists(String taxNumber)throws BusinessException{
        if(corporateCustomerDao.existsCorporateCustomerByTaxNumber(taxNumber)){
            throw new BusinessException("This tax number already exist ");
        }
    }

    private void checkIfCorporateCustomer(int id) throws BusinessException {
        if (!corporateCustomerDao.existsById(id)){
            throw new BusinessException("There is no corporate customer here!");
        }
    }
}
