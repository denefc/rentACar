package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
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
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {

    private BrandDao brandDao;
    private ModelMapperService modelMapperService;


	@Override
	public DataResult<List<BrandListDto>> getAll() {
		List<Brand> result = this.brandDao.findAll();
		List<BrandListDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, "Brand are listed successfully.");
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {


		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

		checkIfBrandNameIsUnique(brand.getBrandName());


		this.brandDao.save(brand);

		return new SuccessResult("Brand is added.");

	}


	@Override
	public DataResult<BrandDto> getById(int brandId) throws BusinessException {

		checkIfBrandExists(brandId);
		Brand brand = this.brandDao.getById(brandId);

		BrandDto response = this.modelMapperService.forDto().map(brand, BrandDto.class);

		return new SuccessDataResult<>(response, "Brand is found by id.");
	}


	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
		checkIfBrandExists(updateBrandRequest.getBrandId());
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);

		checkIfBrandNameIsUnique(brand.getBrandName());

		this.brandDao.save(brand);
		return new SuccessResult("Brand is updated successfuly.");
	}

	@Override
	public Result deleteById(int id) throws BusinessException {
		checkIfBrandExists(id);
		this.brandDao.deleteById(id);
		return new SuccessResult("Brand is deleted successfully.");

	}

	private void checkIfBrandNameIsUnique(String brandName) throws BusinessException {

		for (BrandListDto brandElement : this.getAll().getData()) {
			if (brandElement.getBrandName().equals(brandName)) {
				throw new BusinessException("There can not be more than one brand with the same name.");
			}
		}

	}

	private void checkIfBrandExists(int id) throws BusinessException {
		if(!brandDao.existsById(id)) {
			throw new BusinessException("Brand does not exist by id:" + id);
		}
	}


}