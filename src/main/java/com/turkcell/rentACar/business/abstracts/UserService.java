package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;

public interface UserService {
    void checkIfUserAlreadyExistsByEmail(String email) throws BusinessException;
}
