package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.dtos.*;
import com.turkcell.rentACar.business.requests.createRequests.CreateColorRequest;
import com.turkcell.rentACar.business.requests.createRequests.CreateUserRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateColorRequest;
import com.turkcell.rentACar.business.requests.updateRequests.UpdateUserRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.UserDao;
import com.turkcell.rentACar.entities.concretes.Rental;
import com.turkcell.rentACar.entities.concretes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService {

    private final UserDao userDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public UserManager(UserDao userDao, ModelMapperService modelMapperService) {
        this.userDao = userDao;
        this.modelMapperService = modelMapperService;
    }




    @Override
    public DataResult<List<UserListDto>> getAll() {
        List<User> result = this.userDao.findAll();
        List<UserListDto> response = result.stream().map(user->this.modelMapperService.forDto().map(user,UserListDto.class)).collect(Collectors.toList());


        return new SuccessDataResult<>(response,"Users listed");
    }

    @Override
    public Result add(CreateUserRequest createUserRequest) throws BusinessException {
        return null;
    }

    @Override
    public DataResult<UserDto> getById(int id) throws BusinessException {
        checkIfUserExists(id);
        User user = this.userDao.getById(id);
        UserDto userDtoById =this.modelMapperService.forDto().map(user, UserDto.class);
        return new SuccessDataResult<>(userDtoById,"User listed");

    }

    @Override
    public Result update(UpdateUserRequest updateUserRequest) throws BusinessException {
        return null;
    }

    @Override
    public Result deleteById(int id) throws BusinessException {
        checkIfUserExists(id);
        this.userDao.deleteById(id);
        return new SuccessResult("Rental is deleted.");
    }

    private void checkIfUserExists(int id) throws BusinessException {
        if(!userDao.existsById(id)) {
            throw new BusinessException("User does not exist by id:" + id);
        }
    }


}
