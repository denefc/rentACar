package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {

    private final OrderedAdditionalServiceDao orderedAdditionalServiceDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao, ModelMapperService modelMapperService) {
        this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<OrderedAdditionalServiceListDto>> getAll() {
        List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.findAll();
        List<OrderedAdditionalServiceListDto> response = result.stream()
                .map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService, OrderedAdditionalServiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, "OrderedAdditionalServices are listed successfully.");
    }

    @Override
    public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException {
        OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(createOrderedAdditionalServiceRequest, OrderedAdditionalService.class);

        //rent var mı kontrol et
        orderedAdditionalService.setOrderedAdditionalServiceId(0);
        this.orderedAdditionalServiceDao.save(orderedAdditionalService);

        return new SuccessResult("OrderedAdditionalService is added.");
    }

    @Override
    public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException {
        checkIfOrderedAdditionalServiceExists(updateOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());

        OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(updateOrderedAdditionalServiceRequest, OrderedAdditionalService.class);


        this.orderedAdditionalServiceDao.save(orderedAdditionalService);
        return new SuccessResult("OrderedAdditionalService is updated successfuly.");
    }

    @Override
    public DataResult<OrderedAdditionalServiceDto> getById(int id) throws BusinessException {
        checkIfOrderedAdditionalServiceExists(id);
        OrderedAdditionalService orderedAdditionalService = this.orderedAdditionalServiceDao.getById(id);

        OrderedAdditionalServiceDto response = this.modelMapperService.forDto().map(orderedAdditionalService, OrderedAdditionalServiceDto.class);

        return new SuccessDataResult<>(response, "OrderedAdditionalService is found by id."+id);
    }

    @Override
    public Result deleteById(int id) throws BusinessException {
        checkIfOrderedAdditionalServiceExists(id);
        this.orderedAdditionalServiceDao.deleteById(id);
        return new SuccessResult("OrderedAdditionalService is deleted successfully.");
    }



    private void checkIfOrderedAdditionalServiceExists(int id) throws BusinessException {
        if(!orderedAdditionalServiceDao.existsById(id)) {
            throw new BusinessException("OrderedAdditionalService does not exist by id:" + id);
        }
    }
}
