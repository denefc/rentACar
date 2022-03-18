package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.dtos.CorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/corporate-customers")
public class CorporateCustomersController {

    private final CorporateCustomerService corporateCustomerService;

    @Autowired
    public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
        this.corporateCustomerService = corporateCustomerService;
    }

    @GetMapping("/getall")
    public DataResult<List<CorporateCustomerListDto>> getAll() {
        return corporateCustomerService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
        return corporateCustomerService.add(createCorporateCustomerRequest);
    }

    @GetMapping("/getbyid/{id}")
    public DataResult<CorporateCustomerDto> getById(@RequestParam int id) throws BusinessException {
        return corporateCustomerService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@RequestParam int id) throws BusinessException {
        return corporateCustomerService.delete(id);
    }
}