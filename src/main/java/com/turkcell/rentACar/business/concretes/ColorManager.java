package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACar.business.requests.DeleteColorRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import org.springframework.stereotype.Service;
import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.dtos.ColorDto;
import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.requests.CreateColorRequest;
import com.turkcell.rentACar.business.requests.UpdateColorRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentACar.entities.concretes.Color;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ColorManager implements ColorService {

    private ColorDao colorDao;
    private ModelMapperService modelMapperService;


    @Override
    public DataResult<List<ColorListDto>> getAll() {

        List<Color> result = this.colorDao.findAll();
        List<ColorListDto> response = result.stream()
                .map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<ColorListDto>>(response, "Colors are listed successfully.");

    }

    @Override
    public Result add(CreateColorRequest createColorRequest) throws BusinessException {
        Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);

        checkIfColorNameIsUnique(color.getColorName());


        this.colorDao.save(color);

        return new SuccessResult("Color is added.");
    }

    @Override
    public DataResult<ColorDto> getById(int colorId) throws BusinessException {
        checkIfColorExist(colorId);
        Color color = this.colorDao.getById(colorId);

        ColorDto response = this.modelMapperService.forDto().map(color, ColorDto.class);

        return new SuccessDataResult<ColorDto>(response);
    }


    @Override
    public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {
        checkIfColorExist(updateColorRequest.getColorId());
        Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);

        checkIfColorNameIsUnique(color.getColorName());

        this.colorDao.save(color);

        return new SuccessResult("Color is updated.");
    }

    @Override
    public Result deleteById(DeleteColorRequest deleteColorRequest) throws BusinessException {
        checkIfColorExist(deleteColorRequest.getColorId());
        this.colorDao.deleteById(deleteColorRequest.getColorId());
        return new SuccessResult("Color is deleted.");

    }

    private boolean checkIfColorNameIsUnique(String colorName) throws BusinessException {

        for (ColorListDto colorElement : this.getAll().getData()) {
            if (colorElement.getColorName().equals(colorName)) {
                throw new BusinessException("Aynı isimde birden fazla renk olamaz");
            }
        }

        return true;

    }

    private boolean checkIfColorExist(int id) throws BusinessException {
        if(colorDao.existsById(id) == false) {
            throw new BusinessException("Color does not exist by id:" + id);
        }
        return true;
    }

}