package com.iw3.business;

import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.model.Usuario;

public interface IUsuarioBusiness {

	public Usuario load(String username) throws BusinessException, NotFoundException;
	public boolean isValid(String username,String password) throws BusinessException, NotFoundException;
}
