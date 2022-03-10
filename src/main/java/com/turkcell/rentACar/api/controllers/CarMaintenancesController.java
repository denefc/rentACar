package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.dtos.CarMaintenanceDto;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/carMaintenances")
public class CarMaintenancesController {

    private final CarMaintenanceService carMaintenanceService;

    @Autowired
    public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
        this.carMaintenanceService = carMaintenanceService;
    }

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

    @PutMapping("/update")
    public Result update(@RequestBody UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
        return this.carMaintenanceService.update(updateCarMaintenanceRequest);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@RequestParam int id)  throws BusinessException {

        return this.carMaintenanceService.deleteById(id);
    }

    @GetMapping("/getbycarid")
    public DataResult<List<CarMaintenanceListDto>> getByCarId(@RequestParam(required = true) int carId) throws BusinessException {
        return this.carMaintenanceService.getByCarId(carId);
    }

}
