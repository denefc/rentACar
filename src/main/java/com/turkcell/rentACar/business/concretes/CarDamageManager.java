package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarDamageService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.CarDamageDto;
import com.turkcell.rentACar.business.dtos.CarListDamageDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarDamageDao;
import com.turkcell.rentACar.entities.concretes.CarDamage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class CarDamageManager implements CarDamageService {
    private final CarDamageDao carDamageDao;
    private final ModelMapperService modelMapperService;
    private final CarService carService;

    @Autowired
    public CarDamageManager(CarDamageDao carDamageDao, ModelMapperService modelMapperService, CarService carService) {
        this.carDamageDao = carDamageDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
    }

    @Override
    public DataResult<List<CarListDamageDto>> getAll() {
        List<CarDamage> result = this.carDamageDao.findAll();
        List<CarListDamageDto> response = result.stream()
                .map(carDamage -> this.modelMapperService.forDto().map(carDamage, CarListDamageDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(response, "Car damages listed successfully");
    }

    @Override
    public Result add(CreateCarDamageRequest createCarDamage) throws BusinessException {
        CarDamage carDamage = this.modelMapperService.forRequest().map(createCarDamage, CarDamage.class);
        carService.checkIfCarExists(createCarDamage.getCarId());
        carDamageDao.save(carDamage);
        return new SuccessResult("Car damage added successfully");
    }

    @Override
    public DataResult<CarDamageDto> getById(int id) {
        CarDamage carDamage = this.carDamageDao.getById(id);
        CarDamageDto response = this.modelMapperService.forDto().map(carDamage, CarDamageDto.class);
        return new SuccessDataResult<>(response, "Getting car damage by id");
    }

    @Override
    public Result delete(int id) {
        this.carDamageDao.deleteById(id);
        return new SuccessResult("Car damage deleted successfully");
    }

    @Override
    public Result update(UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException {
        CarDamage carDamage = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);
        carService.checkIfCarExists(updateCarDamageRequest.getCarId());
        carDamageDao.save(carDamage);
        return new SuccessResult("Car damage updated successfully");
    }

}
