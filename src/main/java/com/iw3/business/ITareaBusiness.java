package com.iw3.business;

import java.util.List;

import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.exeptions.TareaException;
import com.iw3.model.Tarea;

public interface ITareaBusiness {
	public void esValido(Tarea tarea) throws TareaException;
	public List<Tarea> getLista() throws BusinessException;
	public void crearTarea(Tarea tarea) throws BusinessException,TareaException;
	public void updateTarea(Tarea tarea) throws BusinessException, TareaException,NotFoundException;
	public Tarea obtenerTarea(Integer id) throws NotFoundException,BusinessException;
}
