package com.turkcell.rentACar.business.concretes;


import java.util.List;
import java.util.stream.Collectors;


import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.CarDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCarRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.dataAccess.abstracts.CarDao;
import com.turkcell.rentACar.entities.concretes.Car;

@Service
public class CarManager implements CarService {

    private final CarDao carDao;
    private final ColorService colorService;
    private final BrandService brandService;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CarManager(CarDao carDao, ColorService colorService, BrandService brandService, ModelMapperService modelMapperService) {
        this.carDao = carDao;
        this.colorService = colorService;
        this.brandService = brandService;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CarListDto>> getAll() {
        List<Car> result = carDao.findAll();

        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateCarRequest createCarRequest) throws BusinessException {
        colorService.checkIfExists(createCarRequest.getColorId());
        brandService.checkIfBrandExists(createCarRequest.getBrandId());

        Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
        this.carDao.save(car);

        return new SuccessResult(BusinessMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {
        checkIfCarExists(updateCarRequest.getCarId());
        colorService.checkIfExists(updateCarRequest.getColorId());
        brandService.checkIfBrandExists(updateCarRequest.getBrandId());

        Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
        this.carDao.save(car);

        return new SuccessResult(BusinessMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public Result deleteById(int id) throws BusinessException {
        checkIfCarExists(id);
        this.carDao.deleteById(id);

        return new SuccessResult(BusinessMessages.DATA_DELETED_SUCCESSFULLY);
    }

    @Override
    public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<Car> result = carDao.findAll(pageable).getContent();
        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(response,BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<List<CarListDto>> getAllSorted(String ascOrDesc) throws BusinessException {
        Sort sort;
        String value = checkIfSortParametersAvailable(ascOrDesc);

        sort = Sort.by(Sort.Direction.valueOf(value), "dailyPrice");

        List<Car> result = carDao.findAll(sort);
        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(response,BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<List<CarListDto>> getByDailyPriceIsLessThanEqual(double dailyPrice) {
        List<Car> result = carDao.getByDailyPriceIsLessThanEqual(dailyPrice);
        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<CarDto> getById(int carId) throws BusinessException {
        checkIfCarExists(carId);
        Car car = this.carDao.getById(carId);

        CarDto response = this.modelMapperService.forDto().map(car, CarDto.class);

        return new SuccessDataResult<>(response);
    }

    @Override
    public DataResult<List<CarListDto>> getByModelYearIsLessThanEqual(int modelYear) {
        List<Car> result = carDao.getByModelYearIsLessThanEqual(modelYear);
        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public void checkIfCarExists(int id) throws BusinessException {
        if (!carDao.existsById(id)) {
            throw new BusinessException(BusinessMessages.CAR_NOT_FOUND + id);
        }
    }

    private String checkIfSortParametersAvailable(String ascOrDesc) throws BusinessException {
        if (!(ascOrDesc.equals("asc") || ascOrDesc.equals("desc"))) {
            throw new BusinessException(BusinessMessages.SORT_PARAMETERS_NOT_AVAILABLE_ERROR_MESSAGE);
        }
        return ascOrDesc.toUpperCase();
    }
}