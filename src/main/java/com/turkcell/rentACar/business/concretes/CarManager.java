package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.*;
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
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class CarManager implements CarService {

    private CarDao carDao;

    private ModelMapperService modelMapperService;


    @Override
    public DataResult<List<CarListDto>> getAll() {
        List<Car> result = carDao.findAll();

        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, "Cars are listed successfuly.");
    }

    @Override
    public Result add(CreateCarRequest createCarRequest) {
        Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);


        this.carDao.save(car);

        return new SuccessResult("Car is added.");
    }

    @Override
    public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {
        checkIfCarExists(updateCarRequest.getCarId());
        Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);

        this.carDao.save(car);

        return new SuccessResult("Car is updated.");
    }

    @Override
    public Result deleteById(int id) throws BusinessException {
        checkIfCarExists(id);
        this.carDao.deleteById(id);
        return new SuccessResult("Car is deleted.");
    }

    @Override
    public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        List<Car> result = carDao.findAll(pageable).getContent();
        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(response);
    }

    @Override
    public DataResult<List<CarListDto>> getAllSorted(String ascOrDesc) throws BusinessException {
        Sort sort;
        String value= checkIsAvailable(ascOrDesc);

        sort = Sort.by(Sort.Direction.valueOf(value),"dailyPrice");

        List<Car> result = carDao.findAll(sort);
        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(response);
    }

    @Override
    public DataResult<List<CarListDto>> getByDailyPriceIsLessThanEqual(double dailyPrice) {
        List<Car> result = carDao.getByDailyPriceIsLessThanEqual(dailyPrice);
        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, "Cars are listed successfuly.");
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
                .map(car->this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, "Cars are listed by less than " + modelYear);
    }

    public void checkIfCarExists(int id) throws BusinessException {
        if(!carDao.existsById(id)) {
            throw new BusinessException("Car does not exist by id:" + id);
        }
    }

    public String checkIsAvailable(String ascOrDesc)throws BusinessException{
        if(!(ascOrDesc.equals("asc") || ascOrDesc.equals("desc"))){
            throw new BusinessException("Please select available sort ");
        }
       return ascOrDesc.toUpperCase();
    }



}