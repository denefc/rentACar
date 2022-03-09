package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.RentalService;
import com.turkcell.rentACar.business.dtos.RentalDto;
import com.turkcell.rentACar.business.dtos.RentalListDto;
import com.turkcell.rentACar.business.requests.CreateRentalRequest;
import com.turkcell.rentACar.business.requests.DeleteRentalRequest;
import com.turkcell.rentACar.business.requests.UpdateRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

    private RentalService rentalService;

    @Autowired
    public RentalsController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/getAll")
   public DataResult<List<RentalListDto>> getAll(){
        return this.rentalService.getAll();

    }

    @GetMapping("/getAllByCarId")
    public DataResult<List<RentalListDto>> getAllByCarCarId(int id) throws BusinessException{
        return this.rentalService.getAllByCarCarId(id);

    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateRentalRequest createRentalRequest) throws BusinessException{
        return this.rentalService.add(createRentalRequest);

    }

    @GetMapping("/getById")
    public DataResult<RentalDto> getById(int id) throws BusinessException{
        return this.rentalService.getById(id);

    }

    @PostMapping("/update")
    public Result update(@RequestBody UpdateRentalRequest updateRentalRequest) throws BusinessException {
        return this.rentalService.update(updateRentalRequest);

    }

    @PostMapping("/delete")
    public Result delete(@RequestBody DeleteRentalRequest deleteRentalRequest) throws BusinessException {
        return this.rentalService.delete(deleteRentalRequest);
    }
}