package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.dtos.CityDto;
import com.turkcell.rentACar.business.dtos.CityListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCityRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateCityRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {

    private final CityService cityService;

    @Autowired
    public CitiesController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CityListDto>> getAll(){
        return this.cityService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCityRequest createCityRequest) throws BusinessException {
        return this.cityService.add(createCityRequest);
    }

    @GetMapping("/getById")
    public DataResult<CityDto> getById(int id) throws BusinessException{
        return this.cityService.getById(id);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCityRequest updateCityRequest) throws BusinessException{
        return this.cityService.update(updateCityRequest);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@RequestParam int id) throws BusinessException{
        return this.cityService.deleteById(id);
    }
}
