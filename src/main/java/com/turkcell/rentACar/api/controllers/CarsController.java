package com.turkcell.rentACar.api.controllers;

import java.util.List;

import com.turkcell.rentACar.business.requests.DeleteCarRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.CarDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.requests.CreateCarRequest;
import com.turkcell.rentACar.business.requests.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import lombok.AllArgsConstructor;

import javax.validation.Valid;

@RequestMapping("/api/cars")
@RestController
@AllArgsConstructor
public class CarsController {

    private CarService carService;

    @GetMapping("/getall")
    public DataResult<List<CarListDto>> getAll() {
        return this.carService.getAll();
    }

    @PostMapping("/add")
    public Result add( @Valid CreateCarRequest createcarRequest) throws BusinessException {

        return this.carService.add(createcarRequest);
    }

    @GetMapping("/getbyid")
    public DataResult<CarDto> getById(@RequestParam(required = true) int carId) throws BusinessException {
        return this.carService.getById(carId);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UpdateCarRequest updatecarRequest) throws BusinessException {
        return this.carService.update(updatecarRequest);
    }

    @PostMapping("/deletebyid")
    public Result deleteById(@RequestBody DeleteCarRequest deleteCarRequest) throws BusinessException {

        return this.carService.deleteById(deleteCarRequest);
    }

    @GetMapping("/getAllPaged")
    public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
        return this.carService.getAllPaged(pageNo, pageSize);
    }

    @GetMapping("/getAllSorted")
    public DataResult<List<CarListDto>> getAllSorted(String ascOrDesc) {
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
