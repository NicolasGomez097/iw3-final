package com.iw3.business;

import java.util.List;

import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.SprintException;
import com.iw3.model.Sprint;

public interface ISprintBusiness {
	public void esValido(Sprint sprint)throws SprintException;
	public void crearSprint(Sprint sprint) throws BusinessException,SprintException;
	public List<Sprint> getLista() throws BusinessException;
}
