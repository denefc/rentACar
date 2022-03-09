package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.BrandDto;
import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.requests.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.DeleteBrandRequest;
import com.turkcell.rentACar.business.requests.UpdateBrandRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;


public interface BrandService {

    DataResult<List<BrandListDto>> getAll();

    Result add(CreateBrandRequest createBrandRequest) throws BusinessException;

    DataResult<BrandDto> getById(int id) throws BusinessException;

    Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;

    Result deleteById(DeleteBrandRequest deleteBrandRequest) throws BusinessException;


}
