package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.dtos.ColorDto;
import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.requests.CreateColorRequest;
import com.turkcell.rentACar.business.requests.DeleteColorRequest;
import com.turkcell.rentACar.business.requests.UpdateColorRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colors")
@AllArgsConstructor
public class ColorsController {

    private ColorService colorService;


    @GetMapping("/getall")
    public DataResult<List<ColorListDto>> getAll() {
        return this.colorService.getAll();
    }

    @GetMapping("/getbyid")
    public DataResult<ColorDto> getById(@RequestParam(required = true) int colorId) throws BusinessException {
        return this.colorService.getById(colorId);
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateColorRequest createColorRequest) throws BusinessException {

        return this.colorService.add(createColorRequest);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UpdateColorRequest updateColorRequest) throws BusinessException {
        return this.colorService.update(updateColorRequest);
    }

    @PostMapping("/deletebyid")
    public Result deleteById(@RequestBody DeleteColorRequest deleteColorRequest) throws BusinessException {

        return this.colorService.deleteById(deleteColorRequest);
    }




}
