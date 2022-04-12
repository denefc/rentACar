package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CityDto;
import com.turkcell.rentACar.business.dtos.CityListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCityRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateCityRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CityDao;
import com.turkcell.rentACar.entities.concretes.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityManager implements CityService {

    private final CityDao cityDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
        this.cityDao = cityDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CityListDto>> getAll() {

        List<City> result = this.cityDao.findAll();
        List<CityListDto> response = result.stream()
                .map(city -> this.modelMapperService.forDto().map(city, CityListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateCityRequest createCityRequest) throws BusinessException {
        checkIfCityNameDuplicate(createCityRequest.getCityName());
        City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
        this.cityDao.save(city);

        return new SuccessResult(BusinessMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public DataResult<CityDto> getById(int id) throws BusinessException {
        checkIfCityExists(id);
        City city = this.cityDao.getById(id);
        CityDto response = this.modelMapperService.forDto().map(city, CityDto.class);

        return new SuccessDataResult<>(response,BusinessMessages.DATA_GET_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateCityRequest updateCityRequest) throws BusinessException {
        checkIfCityExists(updateCityRequest.getCityId());
        City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
        checkIfCityNameDuplicate(updateCityRequest.getCityName());
        this.cityDao.save(city);

        return new SuccessResult(BusinessMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public Result deleteById(int id) throws BusinessException {
        checkIfCityExists(id);
        this.cityDao.deleteById(id);

        return new SuccessResult(BusinessMessages.DATA_DELETED_SUCCESSFULLY);
    }

    @Override
    public void checkIfCityExists(int id) throws BusinessException{
        if(!cityDao.existsById(id)) {
            throw new BusinessException(BusinessMessages.CITY_NOT_FOUND + id);
        }
    }

    private void checkIfCityNameDuplicate(String cityName) throws BusinessException {
        if(cityDao.existsByCityName(cityName)) {
            throw new BusinessException(BusinessMessages.CITY_NAME_ALREADY_EXISTS +cityName);
        }
    }

}