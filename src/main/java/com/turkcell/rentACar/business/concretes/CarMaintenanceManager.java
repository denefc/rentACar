package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.RentalService;
import com.turkcell.rentACar.business.dtos.CarMaintenanceDto;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.RentalListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACar.entities.concretes.CarMaintenance;
import com.turkcell.rentACar.entities.concretes.Rental;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

    private CarMaintenanceDao carMaintenanceDao;
    private ModelMapperService modelMapperService;
    private RentalService rentalService;
    private CarService carService;

    public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService, @Lazy RentalService rentalService, CarService carService) {
        this.carMaintenanceDao = carMaintenanceDao;
        this.modelMapperService = modelMapperService;
        this.rentalService = rentalService;
        this.carService = carService;
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getAll() {
        List<CarMaintenance> result = carMaintenanceDao.findAll();

        List<CarMaintenanceListDto> response = result.stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, "Maintenance are listed successfuly.");
    }

    @Override
    public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
        carService.checkIfCarExists(createCarMaintenanceRequest.getCarCarId());
      //  carService.checkIfCarIsAvailable(createCarMaintenanceRequest.getCarCarId());


        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
        carMaintenance.setCarMaintenanceId(0);
        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult("Maintenance is added.");
    }

    @Override
    public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
        checkIfCarMaintenanceExists(updateCarMaintenanceRequest.getCarMaintenanceId());
       // carService.checkIfCarIsAvailable(updateCarMaintenanceRequest.getCarCarId());
        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);
        carMaintenance.setCarMaintenanceId(0);
        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult("Maintenance is updated.");
    }

    @Override
    public DataResult<CarMaintenanceDto> getById(int carMaintenanceId) throws BusinessException {
        checkIfCarMaintenanceExists(carMaintenanceId);
        CarMaintenance carMaintenance = this.carMaintenanceDao.getById(carMaintenanceId);

        CarMaintenanceDto response = this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceDto.class);

        return new SuccessDataResult<CarMaintenanceDto>(response);
    }

    @Override
    public Result deleteById(int id) throws BusinessException {
        checkIfCarMaintenanceExists(id);
        this.carMaintenanceDao.deleteById(id);
        return new SuccessResult("Maintenance is deleted.");
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getByCarId(int carId) throws BusinessException {
        carService.checkIfCarExists(carId);
        List<CarMaintenance> result = this.carMaintenanceDao.getAllByCar_CarId(carId);
        List<CarMaintenanceListDto> response = result.stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(response, "Car maintenances listed successfully.");
    }

    private void checkIfCarMaintenanceExists(int id) throws BusinessException {
        if (!carMaintenanceDao.existsById(id)) {
            throw new BusinessException("Car maintenance does not exist by id:" + id);
        }
    }


}