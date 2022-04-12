package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.dtos.BrandDto;
import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateBrandRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentACar.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {

    private final BrandDao brandDao;
    private final ModelMapperService modelMapperService;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<BrandListDto>> getAll() {

		List<Brand> result = this.brandDao.findAll();
		List<BrandListDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		checkIfBrandNameDuplicate(brand.getBrandName());

		this.brandDao.save(brand);

		return new SuccessResult(BusinessMessages.DATA_ADDED_SUCCESSFULLY);

	}


	@Override
	public DataResult<BrandDto> getById(int brandId) throws BusinessException {
		checkIfBrandExists(brandId);
		Brand brand = this.brandDao.getById(brandId);

		BrandDto response = this.modelMapperService.forDto().map(brand, BrandDto.class);

		return new SuccessDataResult<>(response, BusinessMessages.DATA_GET_SUCCESSFULLY);
	}


	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
		checkIfBrandExists(updateBrandRequest.getBrandId());
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);

		checkIfBrandNameDuplicate(brand.getBrandName());

		this.brandDao.save(brand);
		return new SuccessResult(BusinessMessages.DATA_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result deleteById(int id) throws BusinessException {
		checkIfBrandExists(id);
		this.brandDao.deleteById(id);

		return new SuccessResult(BusinessMessages.DATA_DELETED_SUCCESSFULLY);
	}

	@Override
	public void checkIfBrandExists(int id) throws BusinessException {
		if(!brandDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.BRAND_ALREADY_EXISTS_ERROR + id);
		}
	}

	private void checkIfBrandNameDuplicate(String brandName) throws BusinessException {
			if (brandDao.existsByBrandName(brandName)){
				throw new BusinessException(BusinessMessages.BRAND_ALREADY_EXISTS_ERROR +brandName);
			}
	}

}