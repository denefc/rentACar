package com.turkcell.rentACar.business.abstracts;

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
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

import java.util.List;

public interface OrderedAdditionalServiceService {

    DataResult<List<OrderedAdditionalServiceListDto>> getAll();

    Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest)throws BusinessException;

    Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException;

    DataResult<OrderedAdditionalServiceDto> getById(int id) throws BusinessException;
    DataResult<List<OrderedAdditionalServiceListDto>> getByRentalId(int rentalId) throws BusinessException;

    Result deleteById(int id) throws BusinessException;

    void checkIfOrderedAdditionalServiceExists(int id) throws BusinessException;

    List<OrderedAdditionalService> getOrderedAdditionalServicesByRentalId(int rentalId) throws BusinessException;

    void orderAdditionalServices(List<Integer> additonalServices, int rentalId) throws BusinessException;


}
