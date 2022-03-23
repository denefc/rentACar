package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.dtos.PaymentDto;
import com.turkcell.rentACar.business.dtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/get-all")
    public DataResult<List<PaymentListDto>> getAll() throws BusinessException {

        return paymentService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreatePaymentRequest createPaymentRequest) throws BusinessException {

        return paymentService.add(createPaymentRequest);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@RequestParam int id) throws BusinessException {

        return paymentService.delete(id);
    }


}
