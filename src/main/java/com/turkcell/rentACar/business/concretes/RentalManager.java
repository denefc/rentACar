package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.RentReturnDto;
import com.turkcell.rentACar.business.dtos.RentalDto;
import com.turkcell.rentACar.business.dtos.RentalListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCarReturnRequest;
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
import com.turkcell.rentACar.entities.concretes.Invoice;
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
    private final CorporateCustomerService corporateCustomerService;
    private final InvoiceService invoiceService;



    @Autowired
    public RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService, @Lazy CarMaintenanceService carMaintenanceService, @Lazy CarService carService, CorporateCustomerService corporateCustomerService,@Lazy InvoiceService invoiceService) {
        this.rentalDao = rentalDao;
        this.modelMapperService = modelMapperService;
        this.carMaintenanceService = carMaintenanceService;
        this.carService = carService;
        this.corporateCustomerService = corporateCustomerService;
        this.invoiceService = invoiceService;
    }

    @Override
    public DataResult<List<RentalListDto>> getAll() {
        List<Rental> result = this.rentalDao.findAll();
        List<RentalListDto> response = result.stream().map(rental->this.modelMapperService.forDto().map(rental,RentalListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(response,"Rents listed");
    }


    @Override
    public Result add(CreateRentalRequest createRentalRequest) throws BusinessException {

        checkIfCarAvailable(createRentalRequest.getCarCarId(),createRentalRequest.getStartDate());
        checkIfLogicallyCarAvailable(createRentalRequest.getStartDate(),createRentalRequest.getEndDate());
        checkIfCarRented(createRentalRequest.getCarCarId(),createRentalRequest.getStartDate());

        Rental rental = this.modelMapperService.forDto().map(createRentalRequest, Rental.class);
        rental.setCustomer(corporateCustomerService.getCustomerById(createRentalRequest.getCustomerId()));
        //farklı city eklencek
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
        checkIfCarAvailable(updateRentalRequest.getCarCarId(),updateRentalRequest.getStartDate());
        checkIfCarRented(updateRentalRequest.getCarCarId(),updateRentalRequest.getStartDate());
        checkIfLogicallyCarAvailable(updateRentalRequest.getStartDate(),updateRentalRequest.getEndDate());

        Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);

        this.rentalDao.save(rental);
        return new SuccessResult("Rent updated");
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


    @Override
    public Rental getRentalById(int id) throws BusinessException {
       checkIfRentalExists(id);
       return rentalDao.getById(id);
    }

    @Override
    public DataResult<RentReturnDto> rentalReturn(CreateCarReturnRequest createCarReturnRequest) {
        Rental rental=this.rentalDao.getById(createCarReturnRequest.getRentalId());
        if(checkReturnDatesEquals(rental,createCarReturnRequest)){
            List<Invoice> invoices=invoiceService.getInvoicesByRentalId(rental.getRentalId());
            double lastTotalPayment=invoices.get(0).getTotalPayment();
            for (int i=1;i<invoices.size();i++){
               lastTotalPayment-=invoices.get(i).getTotalPayment();
            }


        }

        return null;
    }

    private boolean checkReturnDatesEquals(Rental rental,CreateCarReturnRequest createCarReturnRequest){
        if(!rental.getEndDate().equals(createCarReturnRequest.getEndDate())){
           rental.setReturnKilometer(createCarReturnRequest.getReturnKilometer());
           return true;
        }
        return false;
    }




    private void checkIfRentalExists(int id) throws BusinessException {
        if(!rentalDao.existsById(id)) {
            throw new BusinessException("Rental does not exist by id:" + id);
        }
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
