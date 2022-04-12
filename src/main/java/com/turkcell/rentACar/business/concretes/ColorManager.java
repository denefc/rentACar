package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.dtos.ColorDto;
import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateColorRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateColorRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentACar.entities.concretes.Color;
import lombok.AllArgsConstructor;

@Service
public class ColorManager implements ColorService {

    private final ColorDao colorDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
        this.colorDao = colorDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<ColorListDto>> getAll() {
        List<Color> result = this.colorDao.findAll();
        List<ColorListDto> response = result.stream()
                .map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<ColorListDto>>(response,BusinessMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateColorRequest createColorRequest) throws BusinessException {
        Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
        checkIfColorNameDuplicate(color.getColorName());
        this.colorDao.save(color);

        return new SuccessResult(BusinessMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public DataResult<ColorDto> getById(int colorId) throws BusinessException {
        checkIfExists(colorId);
        Color color = this.colorDao.getById(colorId);
        ColorDto response = this.modelMapperService.forDto().map(color, ColorDto.class);

        return new SuccessDataResult<>(response,BusinessMessages.DATA_GET_SUCCESSFULLY);
    }


    @Override
    public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {
        checkIfExists(updateColorRequest.getColorId());
        Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
        checkIfColorNameDuplicate(color.getColorName());
        this.colorDao.save(color);

        return new SuccessResult(BusinessMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public Result deleteById(int id) throws BusinessException {
        checkIfExists(id);
        this.colorDao.deleteById(id);

        return new SuccessResult(BusinessMessages.DATA_DELETED_SUCCESSFULLY);
    }

    @Override
    public void checkIfExists(int id) throws BusinessException {
        if(!colorDao.existsById(id)) {
            throw new BusinessException(BusinessMessages.COLOR_NOT_FOUND_ERROR + id);
        }
    }

    private void checkIfColorNameDuplicate(String colorName) throws BusinessException {
        if (colorDao.existsByColorName(colorName)) {
            throw new BusinessException(BusinessMessages.COLOR_ALREADY_EXISTS + colorName);
        }
    }

}