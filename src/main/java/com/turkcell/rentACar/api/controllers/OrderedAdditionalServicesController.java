package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.dtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.dtos.AdditionalServicesDto;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordered-additional-services")
public class OrderedAdditionalServicesController {

    private final OrderedAdditionalServiceService orderedAdditionalServiceService;

    @Autowired
    public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
    }

    @GetMapping("/getall")
    public DataResult<List<OrderedAdditionalServiceListDto>> getAll() {
        return this.orderedAdditionalServiceService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException {

        return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
    }

    @GetMapping("/getbyid/{id}")
    public DataResult<OrderedAdditionalServiceDto> getById(@RequestParam(required = true) int id) throws BusinessException {
        return this.orderedAdditionalServiceService.getById(id);
    }

    @PutMapping("/update")
    public Result update(@RequestBody UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException {
        return this.orderedAdditionalServiceService.update(updateOrderedAdditionalServiceRequest);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@RequestParam int id) throws BusinessException {

        return this.orderedAdditionalServiceService.deleteById(id);
    }

}
