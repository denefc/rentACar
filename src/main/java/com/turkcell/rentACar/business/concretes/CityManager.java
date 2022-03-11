package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CityService;
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

        return new SuccessDataResult<>(response, "Cities are listed successfully.");
    }

    @Override
    public Result add(CreateCityRequest createCityRequest) throws BusinessException {
        City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
        checkIfCityNameIsUnique(createCityRequest.getCityName());
        this.cityDao.save(city);
        return new SuccessResult("City is added: " + createCityRequest.getCityName());
    }

    @Override
    public DataResult<CityDto> getById(int id) throws BusinessException {
        checkIfCityExists(id);
        City city = this.cityDao.getById(id);
        CityDto response = this.modelMapperService.forDto().map(city, CityDto.class);

        return new SuccessDataResult<>(response);
    }

    @Override
    public Result update(UpdateCityRequest updateCityRequest) throws BusinessException {
        checkIfCityExists(updateCityRequest.getCityId());
        City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
        checkIfCityNameIsUnique(updateCityRequest.getCityName());

        this.cityDao.save(city);

        return new SuccessResult("City is updated.");
    }

    @Override
    public Result deleteById(int id) throws BusinessException {
        checkIfCityExists(id);
        this.cityDao.deleteById(id);
        return new SuccessResult("City is deleted.");
    }

    private void checkIfCityExists(int id) throws BusinessException{
        if(!cityDao.existsById(id)) {
            throw new BusinessException("City does not exist by id:" + id);
        }
    }

    private void checkIfCityNameIsUnique(String cityName) throws BusinessException {

        for (CityListDto cityElement : this.getAll().getData()) {
            if (cityElement.getCityName().equalsIgnoreCase(cityName)) {
                throw new BusinessException("There can not be more than one city with the same name.");
            }
        }

    }

}