package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.dtos.BrandDto;
import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.requests.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.DeleteBrandRequest;
import com.turkcell.rentACar.business.requests.UpdateBrandRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@AllArgsConstructor
public class BrandsController {

    private BrandService brandService;


    @GetMapping("/getall")
    public DataResult<List<BrandListDto>> getAll() {
        return this.brandService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateBrandRequest createBrandRequest) throws BusinessException {

        return this.brandService.add(createBrandRequest);
    }

    @GetMapping("/getbyid")
    public DataResult<BrandDto> getById(@RequestParam(required = true) int brandId) throws BusinessException {
        return this.brandService.getById(brandId);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UpdateBrandRequest updateBrandRequest) throws BusinessException {
        return this.brandService.update(updateBrandRequest);
    }

    @PostMapping("/deletebyid")
    public Result deleteById(@RequestBody DeleteBrandRequest deleteBrandRequest) throws BusinessException {

        return this.brandService.deleteById(deleteBrandRequest);
    }


}