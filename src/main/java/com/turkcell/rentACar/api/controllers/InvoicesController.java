package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.dtos.InvoiceDto;
import com.turkcell.rentACar.business.dtos.InvoiceListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateInvoiceRequest;
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

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
        return invoiceService.add(createInvoiceRequest);
    }

    @GetMapping("/getbyid/{id}")
    public DataResult<InvoiceDto> getById(@RequestParam int id) throws BusinessException {
        return this.invoiceService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@RequestParam int id) throws BusinessException {
        return invoiceService.delete(id);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
        return invoiceService.update(updateInvoiceRequest);
    }


}
