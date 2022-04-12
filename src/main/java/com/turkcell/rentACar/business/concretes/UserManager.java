package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.dataAccess.abstracts.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserManager(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void checkIfUserAlreadyExistsByEmail(String email) throws BusinessException {
        if (userDao.existsByEmail(email)) {
            throw new BusinessException(BusinessMessages.USER_EMAIL_ALREADY_EXISTS + email);
        }

    }
}
