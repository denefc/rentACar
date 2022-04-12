package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.RentReturnDto;
import com.turkcell.rentACar.business.dtos.RentalDto;
import com.turkcell.rentACar.business.dtos.RentalListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCarReturnRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateRentalRequestForCorporateCustomer;
import com.turkcell.rentACar.business.requests.createRequests.CreateRentalRequestForIndividualCustomer;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalManager implements RentalService {

    private final RentalDao rentalDao;
    private final ModelMapperService modelMapperService;
    private final CarMaintenanceService carMaintenanceService;
    private final CarService carService;
    private final OrderedAdditionalServiceService orderedAdditionalServiceService;
    private final AdditionalServiceService additionalServiceService;
    private final CustomerService customerService;
    private final IndividualCustomerService individualCustomerService;
    private final CorporateCustomerService corporateCustomerService;
    private final CityService cityService;

    public RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService,
                         @Lazy CarMaintenanceService carMaintenanceService, @Lazy CarService carService, @Lazy OrderedAdditionalServiceService orderedAdditionalServiceService,
                         @Lazy AdditionalServiceService additionalServiceService, @Lazy CustomerService customerService,
                         CustomerService customerService1, @Lazy IndividualCustomerService individualCustomerService, CityService cityService,
                         @Lazy CorporateCustomerService corporateCustomerService) {

        this.rentalDao = rentalDao;
        this.modelMapperService = modelMapperService;
        this.carMaintenanceService = carMaintenanceService;
        this.carService = carService;
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
        this.additionalServiceService = additionalServiceService;
        this.customerService = customerService1;
        this.individualCustomerService = individualCustomerService;
        this.corporateCustomerService = corporateCustomerService;
        this.cityService = cityService;
    }


    @Override
    public DataResult<List<RentalListDto>> getAll() {
        List<Rental> result = this.rentalDao.findAll();
        List<RentalListDto> response = result.stream().map(rental->this.modelMapperService.forDto().map(rental,RentalListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(response, BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }


    @Override
    public DataResult<Rental> addForIndividualCustomer(CreateRentalRequestForIndividualCustomer createRentalRequestForIndividualCustomer) throws BusinessException {
        customerService.checkIfCustomerExists(createRentalRequestForIndividualCustomer.getIndividualCustomerId());
        carService.checkIfCarExists(createRentalRequestForIndividualCustomer.getCar_CarId());
        individualCustomerService.checkIfIndividualCustomerExists(createRentalRequestForIndividualCustomer.getIndividualCustomerId());
        cityService.checkIfCityExists(createRentalRequestForIndividualCustomer.getReturnLocationIdCityId());
        cityService.checkIfCityExists(createRentalRequestForIndividualCustomer.getPickUpLocationIdCityId());

        checkIfCarAvailable(createRentalRequestForIndividualCustomer.getCar_CarId(),createRentalRequestForIndividualCustomer.getStartDate());
        checkIfLogicallyCarAvailable(createRentalRequestForIndividualCustomer.getStartDate(),createRentalRequestForIndividualCustomer.getEndDate());
        checkIfCarRented(createRentalRequestForIndividualCustomer.getCar_CarId(),createRentalRequestForIndividualCustomer.getStartDate());

        Rental rental = this.modelMapperService.forDto().map(createRentalRequestForIndividualCustomer, Rental.class);

        rental.setRentalId(0);

        rentalDao.saveAndFlush(rental);

        rental.setOrderedAdditionalServices(orderedAdditionalServiceService.getOrderedAdditionalServicesByRentalId(rental.getRentalId()));
        orderedAdditionalServiceService.orderAdditionalServices(createRentalRequestForIndividualCustomer.getAdditionalServicesId(), rental.getRentalId());
        rental.setCustomer(customerService.getCustomerById(createRentalRequestForIndividualCustomer.getIndividualCustomerId()));
        rental.setRentStartKilometer(this.carService.getById(createRentalRequestForIndividualCustomer.getCar_CarId()).getData().getKilometerInformation());

        this.rentalDao.save(rental);
        return new SuccessDataResult<>(rental,BusinessMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public DataResult<Rental> addForCorporateCustomer(CreateRentalRequestForCorporateCustomer createRentalRequestForCorporateCustomer) throws BusinessException {
        customerService.checkIfCustomerExists(createRentalRequestForCorporateCustomer.getCorporateCustomerId());
        carService.checkIfCarExists(createRentalRequestForCorporateCustomer.getCar_CarId());
        corporateCustomerService.checkIfCorporateCustomerExists(createRentalRequestForCorporateCustomer.getCorporateCustomerId());

        checkIfCarAvailable(createRentalRequestForCorporateCustomer.getCar_CarId(),createRentalRequestForCorporateCustomer.getStartDate());
        checkIfLogicallyCarAvailable(createRentalRequestForCorporateCustomer.getStartDate(),createRentalRequestForCorporateCustomer.getEndDate());
        checkIfCarRented(createRentalRequestForCorporateCustomer.getCar_CarId(),createRentalRequestForCorporateCustomer.getStartDate());
        Rental rental = this.modelMapperService.forDto().map(createRentalRequestForCorporateCustomer, Rental.class);

        rental.setRentalId(0);

        rentalDao.saveAndFlush(rental);
        rental.setOrderedAdditionalServices(orderedAdditionalServiceService.getOrderedAdditionalServicesByRentalId(rental.getRentalId()));
        orderedAdditionalServiceService.orderAdditionalServices(createRentalRequestForCorporateCustomer.getAdditionalServicesId(), rental.getRentalId());
        rental.setCustomer(customerService.getCustomerById(createRentalRequestForCorporateCustomer.getCorporateCustomerId()));
        rental.setRentStartKilometer(this.carService.getById(createRentalRequestForCorporateCustomer.getCar_CarId()).getData().getKilometerInformation());

        this.rentalDao.save(rental);
        return new SuccessDataResult<>(rental,BusinessMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public DataResult<RentalDto> getById(int id) throws BusinessException {
        checkIfRentalExists(id);
        Rental rental = this.rentalDao.getById(id);
        RentalDto rentalDtoById =this.modelMapperService.forDto().map(rental, RentalDto.class);
        return new SuccessDataResult<>(rentalDtoById,BusinessMessages.DATA_GET_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateRentalRequest updateRentalRequest) throws BusinessException {
        checkIfRentalExists(updateRentalRequest.getRentalId());
        checkIfCarAvailable(updateRentalRequest.getCarCarId(),updateRentalRequest.getStartDate());
        checkIfCarRented(updateRentalRequest.getCarCarId(),updateRentalRequest.getStartDate());
        checkIfLogicallyCarAvailable(updateRentalRequest.getStartDate(),updateRentalRequest.getEndDate());

        Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);

        this.rentalDao.save(rental);
        return new SuccessResult(BusinessMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public DataResult<List<RentalListDto>> getAllByCarCarId(int id) throws BusinessException {
        this.carService.checkIfCarExists(id);
        List<Rental> result = this.rentalDao.getAllByCar_CarId(id);
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


    @Override
    public Rental getRentalById(int id) throws BusinessException {
       checkIfRentalExists(id);
       return rentalDao.getById(id);
    }

    @Override
    public DataResult<RentReturnDto> rentalReturn(CreateCarReturnRequest createCarReturnRequest) {
        Rental rental=this.rentalDao.getById(createCarReturnRequest.getRentalId());


        return null;
    }

    @Override
    public void checkIfRentalExists(int id) throws BusinessException {
        if(!rentalDao.existsById(id)) {
            throw new BusinessException(BusinessMessages.RENTAL_NOT_FOUND + id);
        }
    }

    private boolean checkReturnDatesEquals(Rental rental,CreateCarReturnRequest createCarReturnRequest){
        if(!rental.getEndDate().equals(createCarReturnRequest.getEndDate())){
           rental.setReturnKilometer(createCarReturnRequest.getReturnKilometer());
           return true;
        }
        return false;
    }



    private void checkIfCarAvailable(int carId ,LocalDate startDate) throws BusinessException {
        DataResult<List<CarMaintenanceListDto>> result = this.carMaintenanceService.getByCarId(carId);
        List<CarMaintenance> response = result.getData().stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenance.class)).collect(Collectors.toList());

        for(CarMaintenance carMaintenance : response) {

            if(carMaintenance.getReturnDate() == null || startDate.isBefore(carMaintenance.getReturnDate())) {
                throw new BusinessException("Car is not available!");
            }
        }
    }
    private void checkIfLogicallyCarAvailable(LocalDate startDate,LocalDate endDate) throws BusinessException {
        if(endDate.isBefore(startDate)){
            throw new BusinessException("End date should be after the Start Date ! ");
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




}
