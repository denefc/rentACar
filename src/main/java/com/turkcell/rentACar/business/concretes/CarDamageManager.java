package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarDamageService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
        return new SuccessDataResult<>(response, BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateCarDamageRequest createCarDamage) throws BusinessException {
        carService.checkIfCarExists(createCarDamage.getCarId());
        CarDamage carDamage = this.modelMapperService.forRequest().map(createCarDamage, CarDamage.class);
        carDamageDao.save(carDamage);

        return new SuccessResult(BusinessMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public DataResult<CarDamageDto> getById(int id) throws BusinessException {
        checkIfCarDamageExists(id);
        CarDamage carDamage = this.carDamageDao.getById(id);
        CarDamageDto response = this.modelMapperService.forDto().map(carDamage, CarDamageDto.class);

        return new SuccessDataResult<>(response, BusinessMessages.DATA_GET_SUCCESSFULLY);
    }

    @Override
    public Result delete(int id) throws BusinessException {
        checkIfCarDamageExists(id);
        this.carDamageDao.deleteById(id);

        return new SuccessResult(BusinessMessages.DATA_DELETED_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException {
        checkIfCarDamageExists(updateCarDamageRequest.getCarDamageId());
        carService.checkIfCarExists(updateCarDamageRequest.getCarId());
        CarDamage carDamage = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);

        carDamageDao.save(carDamage);

        return new SuccessResult(BusinessMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    private void checkIfCarDamageExists(int id) throws BusinessException {
        if(!carDamageDao.existsById(id)) {
            throw new BusinessException(BusinessMessages.CAR_DAMAGE_NOT_FOUND + id);
        }
    }

}
