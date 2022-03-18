package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
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

        return new SuccessDataResult<>(response, "AdditionalServices are listed successfully.");
    }

    @Override
    public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException{
        AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);

        checkIfAdditionalNameIsExists(additionalService.getName());

        additionalService.setAdditionalServiceId(0);
        this.additionalServiceDao.save(additionalService);

        return new SuccessResult("AdditionalService is added.");
    }

    @Override
    public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException {
        checkIfAdditionalServiceExists(updateAdditionalServiceRequest.getAdditionalServiceId());

        AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);


        this.additionalServiceDao.save(additionalService);
        return new SuccessResult("AdditionalService is updated successfuly.");
    }

    @Override
    public DataResult<AdditionalServicesDto> getById(int id) throws BusinessException {
        checkIfAdditionalServiceExists(id);
        AdditionalService additionalService = this.additionalServiceDao.getById(id);

        AdditionalServicesDto response = this.modelMapperService.forDto().map(additionalService, AdditionalServicesDto.class);

        return new SuccessDataResult<>(response, "AdditionalService is found by id."+id);
    }

    @Override
    public Result deleteById(int id) throws BusinessException {
        checkIfAdditionalServiceExists(id);
        this.additionalServiceDao.deleteById(id);
        return new SuccessResult("AdditionalService is deleted successfully.");
    }

    @Override
    public boolean existsAdditionalServiceByAdditionalServiceId(int id) {
        if(additionalServiceDao.existsAdditionalServiceByAdditionalServiceId(id)){
            return true;
        }
        return false;
    }


    private void checkIfAdditionalNameIsExists(String additionalName)throws BusinessException {
            for (AdditionalServiceListDto additionalServiceElement : this.getAll().getData()) {
                if (additionalServiceElement.getAdditionalServiceName().equals(additionalName)) {
                    throw new BusinessException("There can not be more than one AdditionalService with the same name.");
                }
            }
    }

    private void checkIfAdditionalServiceExists(int id) throws BusinessException {
        if(!this.existsAdditionalServiceByAdditionalServiceId(id)) {
            throw new BusinessException("AdditionalService does not exist by id:" + id);
        }
    }
}
