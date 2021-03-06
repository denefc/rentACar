package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.RentalService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {

    private final OrderedAdditionalServiceDao orderedAdditionalServiceDao;
    private final ModelMapperService modelMapperService;
    private final RentalService rentalService;
    private final AdditionalServiceService additionalServiceService;

    @Autowired
    public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao, ModelMapperService modelMapperService, @Lazy RentalService rentalService, AdditionalServiceService additionalServiceService) {
        this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
        this.modelMapperService = modelMapperService;
        this.rentalService = rentalService;
        this.additionalServiceService = additionalServiceService;
    }

    @Override
    public DataResult<List<OrderedAdditionalServiceListDto>> getAll() {
        List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.findAll();
        List<OrderedAdditionalServiceListDto> response = result.stream()
                .map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService, OrderedAdditionalServiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException {
        additionalServiceService.checkIfAdditionalServiceExists(createOrderedAdditionalServiceRequest.getAdditionalServiceAdditionalServiceId());
        rentalService.checkIfRentalExists(createOrderedAdditionalServiceRequest.getRentalId());
        checkIfOrderedAdditionalServiceAlreadyExistsForRentalId(createOrderedAdditionalServiceRequest
                .getAdditionalServiceAdditionalServiceId(),createOrderedAdditionalServiceRequest.getRentalId());

        OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest()
                .map(createOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
        orderedAdditionalService.setOrderedAdditionalServiceId(0);
        this.orderedAdditionalServiceDao.save(orderedAdditionalService);

        return new SuccessResult(BusinessMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException {
        checkIfOrderedAdditionalServiceExists(updateOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());
        additionalServiceService.checkIfAdditionalServiceExists(updateOrderedAdditionalServiceRequest.getAdditionalServiceAdditionalServiceId());
        rentalService.checkIfRentalExists(updateOrderedAdditionalServiceRequest.getRentalId());
        checkIfOrderedAdditionalServiceAlreadyExistsForRentalId(updateOrderedAdditionalServiceRequest.getAdditionalServiceAdditionalServiceId(),updateOrderedAdditionalServiceRequest.getRentalId());

        OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(updateOrderedAdditionalServiceRequest, OrderedAdditionalService.class);


        this.orderedAdditionalServiceDao.save(orderedAdditionalService);
        return new SuccessResult(BusinessMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public DataResult<OrderedAdditionalServiceDto> getById(int id) throws BusinessException {
        checkIfOrderedAdditionalServiceExists(id);
        OrderedAdditionalService orderedAdditionalService = this.orderedAdditionalServiceDao.getById(id);

        OrderedAdditionalServiceDto response = this.modelMapperService.forDto().map(orderedAdditionalService, OrderedAdditionalServiceDto.class);

        return new SuccessDataResult<>(response, BusinessMessages.DATA_GET_SUCCESSFULLY);
    }

    @Override
    public DataResult<List<OrderedAdditionalServiceListDto>> getByRentalId(int rentalId) throws BusinessException {
        rentalService.checkIfRentalExists(rentalId);

        List<OrderedAdditionalService> orderedAdditionalServices = this.orderedAdditionalServiceDao
                .getByRental_RentalId(rentalId);

        List<OrderedAdditionalServiceListDto> orderedAdditionalServiceListDtos = orderedAdditionalServices
                .stream().map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService,
                        OrderedAdditionalServiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(orderedAdditionalServiceListDtos, BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result deleteById(int id) throws BusinessException {
        checkIfOrderedAdditionalServiceExists(id);
        this.orderedAdditionalServiceDao.deleteById(id);
        return new SuccessResult(BusinessMessages.DATA_DELETED_SUCCESSFULLY);
    }


    @Override
    public void checkIfOrderedAdditionalServiceExists(int id) throws BusinessException {
        if(!orderedAdditionalServiceDao.existsById(id)) {
            throw new BusinessException(BusinessMessages.ORDERED_ADDITIONAL_SERVICE_NOT_FOUND+ id);
        }
    }

    @Override
    public List<OrderedAdditionalService> getOrderedAdditionalServicesByRentalId(int rentalId) throws BusinessException {
        rentalService.checkIfRentalExists(rentalId);

        return this.orderedAdditionalServiceDao.getByRental_RentalId(rentalId);
    }

    @Override
    public void orderAdditionalServices(List<Integer> additonalServices, int rentalId) throws BusinessException {
        for(int i = 0; i<additonalServices.size(); i++) {
            additionalServiceService.checkIfAdditionalServiceExists(additonalServices.get(i));
            CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest = new CreateOrderedAdditionalServiceRequest(additonalServices.get(i),rentalId);
            createOrderedAdditionalServiceRequest.setAdditionalServiceAdditionalServiceId(additonalServices.get(i));
            createOrderedAdditionalServiceRequest.setRentalId(rentalId);
            this.add(createOrderedAdditionalServiceRequest);
        }
    }

    private void checkIfOrderedAdditionalServiceAlreadyExistsForRentalId(int additionalServiceId, int rentalId)
            throws BusinessException {
        if (this.orderedAdditionalServiceDao.existsByAdditionalService_AdditionalServiceIdAndRental_RentalId(
                additionalServiceId, rentalId)) {
            throw new BusinessException(BusinessMessages.ORDERED_ADDITIONAL_SERVICE_ALREADY_EXISTS+rentalId);
        }

    }

}
