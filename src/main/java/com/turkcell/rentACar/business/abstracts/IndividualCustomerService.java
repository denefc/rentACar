package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.IndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface IndividualCustomerService {

    DataResult<List<IndividualCustomerListDto>> getAll();

    Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest);

    DataResult<IndividualCustomerDto> getById(int id);

    Result delete(int id);

    Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest);
}
