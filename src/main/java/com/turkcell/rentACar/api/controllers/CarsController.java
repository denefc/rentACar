package com.turkcell.rentACar.api.controllers;

import java.util.List;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.CarDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCarRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;



import javax.validation.Valid;

@RequestMapping("/api/cars")
@RestController
public class CarsController {

    private final CarService carService;

    @Autowired
    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/get-all")
    public DataResult<List<CarListDto>> getAll() {
        return this.carService.getAll();
    }

    @PostMapping("/add")
    public Result add( @Valid @RequestBody CreateCarRequest createcarRequest) throws BusinessException {
        return this.carService.add(createcarRequest);
    }

    @GetMapping("/get-byid")
    public DataResult<CarDto> getById(@RequestParam int carId) throws BusinessException {
        return this.carService.getById(carId);
    }

    @PutMapping("/update")
    public Result update(@RequestBody UpdateCarRequest updatecarRequest) throws BusinessException {
        return this.carService.update(updatecarRequest);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@RequestParam int id) throws BusinessException {

        return this.carService.deleteById(id);
    }

    @GetMapping("/getAllPaged")
    public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
        return this.carService.getAllPaged(pageNo, pageSize);
    }

    @GetMapping("/getAllSorted")
    public DataResult<List<CarListDto>> getAllSorted(String ascOrDesc)throws BusinessException  {
        return this.carService.getAllSorted(ascOrDesc);
    }

    @GetMapping("/sortAllByDailyPrice")
    public DataResult<List<CarListDto>> getByDailyPriceIsLessThanEqual(double dailyPrice) {
        return this.carService.getByDailyPriceIsLessThanEqual(dailyPrice);
    }

    @GetMapping("/sortByModelYear")
    public DataResult<List<CarListDto>> getByModelYearIsLessThanEqual(int modelYear) {
        return this.carService.getByModelYearIsLessThanEqual(modelYear);
    }


}
