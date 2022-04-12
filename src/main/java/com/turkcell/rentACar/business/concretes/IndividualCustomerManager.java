package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.IndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
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
    private final UserService userService;

    @Autowired
    public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService, UserService userService) {
        this.individualCustomerDao = individualCustomerDao;
        this.modelMapperService = modelMapperService;
        this.userService = userService;
    }

    @Override
    public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {
        userService.checkIfUserAlreadyExistsByEmail(createIndividualCustomerRequest.getEmail());
        checkIfIndividualCustomerAlreadyExistsByNationalIdentity(createIndividualCustomerRequest.getNationalIdentity());

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
                .map(createIndividualCustomerRequest, IndividualCustomer.class);

        individualCustomer.setDateRegistered(LocalDate.now());
        individualCustomerDao.save(individualCustomer);
        return new SuccessResult(BusinessMessages.DATA_ADDED_SUCCESSFULLY);

    }

    @Override
    public Result delete(int id) {
        this.individualCustomerDao.deleteById(id);
        return new SuccessResult(BusinessMessages.DATA_DELETED_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {
        checkIfIndividualCustomerAlreadyExistsByNationalIdentity(updateIndividualCustomerRequest.getNationalIdentity());
        checkIfIndividualCustomerExists(updateIndividualCustomerRequest.getId());
        userService.checkIfUserAlreadyExistsByEmail(updateIndividualCustomerRequest.getEmail());

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
                .map(updateIndividualCustomerRequest, IndividualCustomer.class);
        individualCustomerDao.save(individualCustomer);

        return new SuccessResult(BusinessMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public DataResult<IndividualCustomerDto> getById(int id) {
        IndividualCustomer individualCustomer = this.individualCustomerDao.getById(id);
        IndividualCustomerDto response = this.modelMapperService.forDto().map(individualCustomer,
                IndividualCustomerDto.class);
        return new SuccessDataResult<>(response, BusinessMessages.DATA_GET_SUCCESSFULLY);
    }

    @Override
    public DataResult<List<IndividualCustomerListDto>> getAll() {
        List<IndividualCustomer> result = this.individualCustomerDao.findAll();
        List<IndividualCustomerListDto> response = result.stream().map(individualCustomer -> this.modelMapperService
                .forDto().map(individualCustomer, IndividualCustomerListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(response,BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public void checkIfIndividualCustomerExists(int individualCustomerId) throws BusinessException {
        if (!this.individualCustomerDao.existsById(individualCustomerId)) {
            throw new BusinessException(
                    BusinessMessages.INDIVIDUAL_CUSTOMER_NOT_FOUND + individualCustomerId);
        }
    }

    private void checkIfIndividualCustomerAlreadyExistsByNationalIdentity(String nationalIdentity) throws BusinessException {
        if (this.individualCustomerDao.existsByNationalIdentity(nationalIdentity)) {
            throw new BusinessException(BusinessMessages.INDIVIDUAL_CUSTOMER_NATIONAL_IDENTITY_ALREADY_EXISTS + nationalIdentity);
        }
    }




}
