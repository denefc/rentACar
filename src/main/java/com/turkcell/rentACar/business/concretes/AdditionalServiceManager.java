package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.dtos.AdditionalServicesDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.AdditionalService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {

    private final AdditionalServiceDao additionalServiceDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
        this.additionalServiceDao = additionalServiceDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<AdditionalServiceListDto>> getAll() {
        List<AdditionalService> result = this.additionalServiceDao.findAll();
        List<AdditionalServiceListDto> response = result.stream()
                .map(additionalService -> this.modelMapperService.forDto().map(additionalService, AdditionalServiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException{
        checkIfAdditionalNameDuplicate(createAdditionalServiceRequest.getAdditionalServiceName());
        AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
        additionalService.setAdditionalServiceId(0);
        this.additionalServiceDao.save(additionalService);

        return new SuccessResult(BusinessMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException {
        checkIfAdditionalServiceExists(updateAdditionalServiceRequest.getAdditionalServiceId());

        AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);


        this.additionalServiceDao.save(additionalService);
        return new SuccessResult(BusinessMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public DataResult<AdditionalServicesDto> getById(int id) throws BusinessException {
        checkIfAdditionalServiceExists(id);
        AdditionalService additionalService = this.additionalServiceDao.getById(id);

        AdditionalServicesDto response = this.modelMapperService.forDto().map(additionalService, AdditionalServicesDto.class);

        return new SuccessDataResult<>(response,BusinessMessages.DATA_GET_SUCCESSFULLY);
    }

    @Override
    public Result deleteById(int id) throws BusinessException {
        checkIfAdditionalServiceExists(id);
        this.additionalServiceDao.deleteById(id);

        return new SuccessResult(BusinessMessages.DATA_DELETED_SUCCESSFULLY);
    }

    @Override
    public void checkIfAdditionalServiceExists(int id) throws BusinessException {
        if(!additionalServiceDao.existsById(id)) {
            throw new BusinessException(BusinessMessages.ADDITIONAL_SERVICE_NOT_FOUND + id);
        }
    }

    @Override
    public double calculateAdditionalPriceOfServices(List<Integer> orderedAdditionalServices) {
        double additionalServicePrice = 0;
        for (int i = 0; i < orderedAdditionalServices.size(); i++) {
            additionalServicePrice = additionalServicePrice + this.additionalServiceDao.getById(orderedAdditionalServices.get(i)).getDailyPrice();
        }
        return additionalServicePrice;
    }


    private void checkIfAdditionalNameDuplicate(String additionalName)throws BusinessException {
        if (additionalServiceDao.existsByName(additionalName)) {
            throw new BusinessException(BusinessMessages.ADDITIONAL_SERVICE_ALREADY_EXISTS+additionalName);
        }
    }


}
