package com.iw3.controller;

import java.util.List;

import com.iw3.exeptions.BusinessException;
import com.iw3.model.Sprint;

public interface ISprintController {
	public boolean esValido(Sprint sprint);
	public void crearSprint(Sprint sprint) throws BusinessException;
	public List<Sprint> getLista() throws BusinessException;
}
