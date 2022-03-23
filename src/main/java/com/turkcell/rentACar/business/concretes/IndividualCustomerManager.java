package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.dtos.IndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentACar.entities.concretes.IndividualCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

    private final IndividualCustomerDao individualCustomerDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService) {
        this.individualCustomerDao = individualCustomerDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {
        IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
                .map(createIndividualCustomerRequest, IndividualCustomer.class);

        individualCustomer.setDateRegistered(LocalDate.now());
        individualCustomerDao.save(individualCustomer);
        return new SuccessResult("Individual customer added successfully.");

    }

    @Override
    public Result delete(int id) {
        this.individualCustomerDao.deleteById(id);
        return new SuccessResult("Individual customer deleted successfully.");
    }

    @Override
    public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
        IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
                .map(updateIndividualCustomerRequest, IndividualCustomer.class);
        individualCustomerDao.save(individualCustomer);
        return new SuccessResult("Individual customer updated successfully.");
    }

    @Override
    public DataResult<IndividualCustomerDto> getById(int id) {
        IndividualCustomer individualCustomer = this.individualCustomerDao.getById(id);
        IndividualCustomerDto response = this.modelMapperService.forDto().map(individualCustomer,
                IndividualCustomerDto.class);
        return new SuccessDataResult<>(response, "Getting individual customer by id");
    }

    @Override
    public DataResult<List<IndividualCustomerListDto>> getAll() {
        List<IndividualCustomer> result = this.individualCustomerDao.findAll();
        List<IndividualCustomerListDto> response = result.stream().map(individualCustomer -> this.modelMapperService
                .forDto().map(individualCustomer, IndividualCustomerListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(response,"Individual customers listed successfully.");
    }

}
