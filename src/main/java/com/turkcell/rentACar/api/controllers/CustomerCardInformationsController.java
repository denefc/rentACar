package com.turkcell.rentACar.api.controllers;


import com.turkcell.rentACar.business.abstracts.CustomerCardInformationService;
import com.turkcell.rentACar.business.dtos.CustomerCardInformationListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCustomerCardInformationRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-card-information")
public class CustomerCardInformationsController {
    private final CustomerCardInformationService customerCardInformationService;

    @Autowired
    public CustomerCardInformationsController(CustomerCardInformationService customerCardInformationService) {
        this.customerCardInformationService = customerCardInformationService;
    }

    @GetMapping("/get-all")
    public DataResult<List<CustomerCardInformationListDto>> getAll() throws BusinessException {

        return customerCardInformationService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateCustomerCardInformationRequest createCustomerCardInformationRequest) throws BusinessException {


        return customerCardInformationService.add(createCustomerCardInformationRequest);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@RequestParam int id) throws BusinessException {

        return customerCardInformationService.deleteById(id);
    }

}
