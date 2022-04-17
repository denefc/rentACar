package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.dtos.InvoiceDto;
import com.turkcell.rentACar.business.dtos.InvoiceListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/invoices")
@RestController
public class InvoicesController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoicesController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/get-all")
    public DataResult<List<InvoiceListDto>> getAll() {
        return invoiceService.getAll();
    }

    @GetMapping("/get-invoices-by-rental-id/{id}")
    public DataResult<List<InvoiceListDto>> getAllInvoicesByRentalId(@RequestParam @PathVariable int id){
        return this.invoiceService.getInvoicesByRentalId(id);
    }


    @GetMapping("/get-by-id/{id}")
    public DataResult<InvoiceDto> getById(@RequestParam @PathVariable int id) throws BusinessException {
        return this.invoiceService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@RequestParam @PathVariable int id) throws BusinessException {
        return invoiceService.delete(id);
    }




}
