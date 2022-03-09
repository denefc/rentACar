package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.requests.DeleteCarRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.CarDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.requests.CreateCarRequest;
import com.turkcell.rentACar.business.requests.UpdateCarRequest;
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

        return new SuccessDataResult<List<CarListDto>>(response, "Cars are listed successfuly.");
    }

    @Override
    public Result add(CreateCarRequest createCarRequest) {
        Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);

        this.carDao.save(car);

        return new SuccessResult("Car is added.");
    }

    @Override
    public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {
        checkIfCarExist(updateCarRequest.getCarId());
        Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);

        this.carDao.save(car);

        return new SuccessResult("Car is updated.");
    }

    @Override
    public Result deleteById(DeleteCarRequest deleteCarRequest) throws BusinessException {
        checkIfCarExist(deleteCarRequest.getCarId());
        this.carDao.deleteById(deleteCarRequest.getCarId());
        return new SuccessResult("Car is deleted.");
    }

    @Override
    public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        List<Car> result = carDao.findAll(pageable).getContent();
        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CarListDto>>(response);
    }

    @Override
    public DataResult<List<CarListDto>> getAllSorted(String ascOrDesc) {
        Sort sort;
        String value = (ascOrDesc.equals("ASC") || ascOrDesc.equals("DESC") ) ? ascOrDesc : "DESC";

        sort = Sort.by(Sort.Direction.valueOf(value),"dailyPrice");

        List<Car> result = carDao.findAll(sort);
        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CarListDto>>(response);
    }

    @Override
    public DataResult<List<CarListDto>> getByDailyPriceIsLessThanEqual(double dailyPrice) {
        List<Car> result = carDao.getByDailyPriceIsLessThanEqual(dailyPrice);
        List<CarListDto> response = result.stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarListDto>>(response, "Cars are listed successfuly.");
    }

    @Override
    public DataResult<CarDto> getById(int carId) throws BusinessException {
        checkIfCarExist(carId);
        Car car = this.carDao.getById(carId);

        CarDto response = this.modelMapperService.forDto().map(car, CarDto.class);

        return new SuccessDataResult<CarDto>(response);
    }

    @Override
    public DataResult<List<CarListDto>> getByModelYearIsLessThanEqual(int modelYear) {
        List<Car> result = carDao.getByModelYearIsLessThanEqual(modelYear);
        List<CarListDto> response = result.stream()
                .map(car->this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarListDto>>(response, "Cars are listed by less than " + modelYear);
    }

    public boolean checkIfCarExist(int id) throws BusinessException {
        if(carDao.existsById(id) == false) {
            throw new BusinessException("Car does not exist by id:" + id);
        }
        return true;
    }

}