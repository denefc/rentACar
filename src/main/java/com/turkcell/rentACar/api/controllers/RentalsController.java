package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.RentalService;
import com.turkcell.rentACar.business.dtos.RentalDto;
import com.turkcell.rentACar.business.dtos.RentalListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateRentalRequestForCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateRentalRequestForIndividualCustomer;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

    private final RentalService rentalService;

    @Autowired
    public RentalsController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/get-all")
   public DataResult<List<RentalListDto>> getAll(){
        return this.rentalService.getAll();

    }

    @GetMapping("/get-all-by-car-id")
    public DataResult<List<RentalListDto>> getAllByCarCarId(@RequestParam int id) throws BusinessException{
        return this.rentalService.getAllByCarCarId(id);

    }



    @GetMapping("/get-by-id")
    public DataResult<RentalDto> getById(int id) throws BusinessException{
        return this.rentalService.getById(id);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateRentalRequest updateRentalRequest) throws BusinessException {
        return this.rentalService.update(updateRentalRequest);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@RequestParam int id)  throws BusinessException {
        return this.rentalService.deleteById(id);
    }
}