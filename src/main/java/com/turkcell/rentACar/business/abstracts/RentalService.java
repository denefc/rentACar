package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.RentReturnDto;
import com.turkcell.rentACar.business.dtos.RentalDto;
import com.turkcell.rentACar.business.dtos.RentalListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateCarReturnRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateRentalRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateRentalRequestForCorporateCustomer;
import com.turkcell.rentACar.business.requests.createRequests.CreateRentalRequestForIndividualCustomer;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.Rental;

import java.util.List;



public interface RentalService {
    DataResult<List<RentalListDto>> getAll();

    DataResult<List<RentalListDto>> getAllByCarCarId(int id) throws BusinessException;

    DataResult<Rental> addForIndividualCustomer(CreateRentalRequestForIndividualCustomer createRentalRequestForIndividualCustomer) throws BusinessException;

    DataResult<Rental> addForCorporateCustomer(CreateRentalRequestForCorporateCustomer createRentalRequestForCorporateCustomer) throws BusinessException;

    DataResult<RentalDto> getById(int id) throws BusinessException;

    Result update(UpdateRentalRequest updateRentalRequest) throws BusinessException;

    Result deleteById(int id) throws BusinessException;

    Rental getRentalById(int id) throws BusinessException;

    DataResult<RentReturnDto>rentalReturn(CreateCarReturnRequest createCarReturnRequest);

    void checkIfRentalExists(int id)throws BusinessException;

}
