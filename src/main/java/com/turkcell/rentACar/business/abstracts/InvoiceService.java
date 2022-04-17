package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.*;
import com.turkcell.rentACar.business.requests.createRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateRentalRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateInvoiceRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.Invoice;

import java.util.List;

public interface InvoiceService {
    DataResult<List<InvoiceListDto>> getAll();

    Invoice add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException;

    DataResult<InvoiceDto> getById(int id) throws BusinessException;

    Result delete(int id) throws BusinessException;

    DataResult<List<InvoiceListDto>> getInvoicesByRentalId(int rentalId);

}
