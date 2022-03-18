package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.RentalService;

import com.turkcell.rentACar.business.dtos.InvoiceDto;
import com.turkcell.rentACar.business.dtos.InvoiceListDto;

import com.turkcell.rentACar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.InvoiceDao;

import com.turkcell.rentACar.entities.concretes.Invoice;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;
import com.turkcell.rentACar.entities.concretes.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceManager implements InvoiceService {

    private final InvoiceDao invoiceDao;
    private final ModelMapperService modelMapperService;
    private final RentalService rentalService;

    @Autowired
    public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, RentalService rentalService) {
        this.invoiceDao = invoiceDao;
        this.modelMapperService = modelMapperService;
        this.rentalService = rentalService;
    }

    @Override
    public DataResult<List<InvoiceListDto>> getAll() {
        List<Invoice> result = this.invoiceDao.findAll();
        List<InvoiceListDto> response = result.stream()
                .map(invoice->this.modelMapperService.forDto().map(invoice,InvoiceListDto.class)).collect(Collectors.toList());


        return new SuccessDataResult<>(response,"Invoices listed");
    }

    @Override
    public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
        double totalPayment = 0;
        Invoice invoice = this.modelMapperService.forDto().map(createInvoiceRequest, Invoice.class);
       totalPayment+=differentCityPayment(createInvoiceRequest.getRentalId());
       totalPayment+=daysOfRentalPayment(createInvoiceRequest.getRentalId());
       totalPayment+=orderedAdditionalPayment(createInvoiceRequest.getRentalId());

       invoice.setInvoiceDate( LocalDate.now());

        invoice.setTotalPayment(totalPayment);

        invoice.setInvoiceId(0);
        this.invoiceDao.save(invoice);
        return new SuccessResult("Invoice is created");
    }

    @Override
    public DataResult<InvoiceDto> getById(int id) throws BusinessException {

        checkIfInvoiceExist(id);
        Invoice invoice = this.invoiceDao.getById(id);

        InvoiceDto response = this.modelMapperService.forDto().map(invoice, InvoiceDto.class);

        return new SuccessDataResult<>(response);
    }

    @Override
    public Result delete(int id) throws BusinessException {
        checkIfInvoiceExist(id);
        this.invoiceDao.deleteById(id);
        return new SuccessResult("Deleted successfully");
    }

    @Override
    public Result update( UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
        Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);

        checkIfInvoiceExist(updateInvoiceRequest.getId());

        invoice.setInvoiceId(0);

        this.invoiceDao.save(invoice);

        return new SuccessResult("Invoice updated successfully");
    }

    private double differentCityPayment(int rentalId) throws BusinessException {
        Rental rental=rentalService.getRentalById(rentalId);

        if(rental.getCityOfPickUpLocation().getCityId()!=rental.getCityOfPickUpLocation().getCityId()){
            return 750;
        }
        return 0;
    }

    private double daysOfRentalPayment(int rentalId) throws BusinessException {
        Rental rental=rentalService.getRentalById(rentalId);
        int rentedDayValue = (int) ChronoUnit.DAYS.between(rental.getStartDate(),rental.getEndDate()) + 1;
        double dailyCarPrice=rental.getCar().getDailyPrice();

        return dailyCarPrice*rentedDayValue;
    }
    private double orderedAdditionalPayment(int rentalId)throws BusinessException{
        Rental rental=rentalService.getRentalById(rentalId);
        int daysOfRental=daysOfRental(rentalId);
        double totalAdditionalPayment=0;
        for (OrderedAdditionalService orderedItems:rental.getOrderedAdditionalServices()){
            totalAdditionalPayment+=orderedItems.getAdditionalService().getDailyPrice()*daysOfRental;
        }
        return totalAdditionalPayment;

    }


    private int daysOfRental(int rentalId)throws BusinessException{
        Rental rental=rentalService.getRentalById(rentalId);
        return (int) ChronoUnit.DAYS.between(rental.getStartDate(),rental.getEndDate()) + 1;
    }

    private void checkIfInvoiceExist(int id) throws BusinessException {
        if(!this.invoiceDao.existsById(id)){
            throw new BusinessException("There is no invoice with following id: "+id);
        }
    }

}
