package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.ColorDto;
import com.turkcell.rentACar.business.dtos.UserDto;
import com.turkcell.rentACar.business.dtos.UserListDto;
import com.turkcell.rentACar.business.requests.createRequests.CreateColorRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateUserRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateColorRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateUserRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface UserService {
    DataResult<List<UserListDto>> getAll();

    Result add(CreateUserRequest createUserRequest) throws BusinessException;

    DataResult<UserDto> getById(int id) throws BusinessException;

    Result update(UpdateUserRequest updateUserRequest) throws BusinessException;

    Result deleteById(int id) throws BusinessException;

}
