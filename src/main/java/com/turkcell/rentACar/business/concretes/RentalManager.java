package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.RentalService;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.RentalDto;
import com.turkcell.rentACar.business.dtos.RentalListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateRentalRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.RentalDao;
import com.turkcell.rentACar.entities.concretes.CarMaintenance;
import com.turkcell.rentACar.entities.concretes.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalManager implements RentalService {



    private final RentalDao rentalDao;
    private final ModelMapperService modelMapperService;
    private final CarMaintenanceService carMaintenanceService;
    private final CarService carService;

    @Autowired
    public RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService,
                         @Lazy CarMaintenanceService carMaintenanceService, @Lazy CarService carService) {

        this.rentalDao = rentalDao;
        this.modelMapperService = modelMapperService;
        this.carMaintenanceService = carMaintenanceService;
        this.carService = carService;
    }

    @Override
    public DataResult<List<RentalListDto>> getAll() {
        List<Rental> result = this.rentalDao.findAll();
        List<RentalListDto> response = result.stream().map(rental->this.modelMapperService.forDto().map(rental,RentalListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(response,"Rents listed");
    }


    @Override
    public Result add(CreateRentalRequest createRentalRequest) throws BusinessException {

        checkIfCarAvailable(createRentalRequest);
        checkIfLogicallyCarAvailable(createRentalRequest.getStartDate(),createRentalRequest.getEndDate());
        checkIfCarRented(createRentalRequest.getCarCarId(),createRentalRequest.getStartDate());
        Rental rental = this.modelMapperService.forDto().map(createRentalRequest, Rental.class);
        rental.setRentalId(0);
        this.rentalDao.save(rental);
        return new SuccessResult("Rent is added");
    }

    @Override
    public DataResult<RentalDto> getById(int id) throws BusinessException {
        checkIfRentalExists(id);
        Rental rental = this.rentalDao.getById(id);
        RentalDto rentalDtoById =this.modelMapperService.forDto().map(rental, RentalDto.class);
        return new SuccessDataResult<>(rentalDtoById,"Rent listed");
    }

    @Override
    public Result update(UpdateRentalRequest updateRentalRequest) throws BusinessException {
        checkIfRentalExists(updateRentalRequest.getRentalId());
        checkIfCarAvailableForUpdate(updateRentalRequest);
        checkIfCarRented(updateRentalRequest.getCarCarId(),updateRentalRequest.getStartDate());
        checkIfLogicallyCarAvailable(updateRentalRequest.getStartDate(),updateRentalRequest.getEndDate());
        Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
        this.rentalDao.save(rental);
        return new SuccessResult("Rent updated");
    }

    private void checkIfCarAvailable(CreateRentalRequest createRentalRequest) throws BusinessException {
        DataResult<List<CarMaintenanceListDto>> result = this.carMaintenanceService.getByCarId(createRentalRequest.getCarCarId());
        List<CarMaintenance> response = result.getData().stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenance.class)).collect(Collectors.toList());

        for(CarMaintenance carMaintenance : response) {
            //8 11                          //11 Mart
            if(carMaintenance.getReturnDate() == null || createRentalRequest.getStartDate().isBefore(carMaintenance.getReturnDate())) {
                throw new BusinessException("Car is not available!");
            }
        }
    }
    private void checkIfLogicallyCarAvailable(LocalDate startDate,LocalDate endDate) throws BusinessException {
      if(endDate.isBefore(startDate)){
          throw new BusinessException("End date should be after the Start Date ! ");
      }
    }
    private void checkIfCarAvailableForUpdate(UpdateRentalRequest updateRentalRequest) throws BusinessException {
        checkIfRentalExists(updateRentalRequest.getRentalId());
        DataResult<List<CarMaintenanceListDto>> result = this.carMaintenanceService.getByCarId(updateRentalRequest.getCarCarId());
        List<CarMaintenance> response = result.getData().stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenance.class)).collect(Collectors.toList());


        for(CarMaintenance carMaintenance : response) {
            if(carMaintenance.getReturnDate() == null || updateRentalRequest.getStartDate().isBefore(carMaintenance.getReturnDate())) {
                throw new BusinessException("Car is not available!");
            }
        }
    }

    private void checkIfCarRented(int id,LocalDate newStartDate) throws BusinessException{
        this.carService.checkIfCarExists(id);
        DataResult<List<RentalListDto>> result=getAllByCarCarId(id);
        List<Rental> response = result.getData().
                stream().
                map(rental -> this.modelMapperService.forDto().map(rental, Rental.class)).collect(Collectors.toList());

        for(Rental rental:response){
            if (!rental.getEndDate().isBefore(newStartDate)){
                throw new BusinessException("Car is rented those dates!");
            }
        }

    }

    @Override
    public DataResult<List<RentalListDto>> getAllByCarCarId(int id) throws BusinessException {
        this.carService.checkIfCarExists(id);
        List<Rental> result = this.rentalDao.getAllByCarCarId(id);
        List<RentalListDto> response = result.stream().map(rent -> this.modelMapperService.forDto().map(rent, RentalListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(response,"Car's rent info listed");
    }

    @Override
    public Result deleteById(int id) throws BusinessException {
        checkIfRentalExists(id);
        this.rentalDao.deleteById(id);
        return new SuccessResult("Rental is deleted.");
    }

    private void checkIfRentalExists(int id) throws BusinessException {
        if(!rentalDao.existsById(id)) {
            throw new BusinessException("Rental does not exist by id:" + id);
        }
    }



}
