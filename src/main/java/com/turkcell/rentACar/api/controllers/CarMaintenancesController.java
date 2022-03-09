package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.dtos.CarDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.dtos.CarMaintenanceDto;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.requests.*;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.CarMaintenance;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/carMaintenances")
@AllArgsConstructor
public class CarMaintenancesController {

    private CarMaintenanceService carMaintenanceService;

    @GetMapping("/getall")
    public DataResult<List<CarMaintenanceListDto>> getAll() {
        return this.carMaintenanceService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCarMaintenanceRequest createcarMaintenanceRequest) throws BusinessException {

        return this.carMaintenanceService.add(createcarMaintenanceRequest);
    }

    @GetMapping("/getbyid")
    public DataResult<CarMaintenanceDto> getById(@RequestParam(required = true) int carMaintenanceId) throws BusinessException {
        return this.carMaintenanceService.getById(carMaintenanceId);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
        return this.carMaintenanceService.update(updateCarMaintenanceRequest);
    }

    @PostMapping("/deletebyid")
    public Result deleteById(@RequestBody DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {

        return this.carMaintenanceService.deleteById(deleteCarMaintenanceRequest);
    }

    @GetMapping("/getbycarid")
    public DataResult<List<CarMaintenanceListDto>> getByCarId(@RequestParam(required = true) int carId) throws BusinessException {
        return this.carMaintenanceService.getByCarId(carId);
    }

}
