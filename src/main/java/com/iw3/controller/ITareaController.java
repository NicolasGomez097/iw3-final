package com.iw3.controller;

import java.util.List;

import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.model.Tarea;

public interface ITareaController {
	public boolean esValido(Tarea tarea);
	public List<Tarea> getLista() throws BusinessException;
	public void crearTarea(Tarea tarea) throws BusinessException;
	public Tarea obtenerTarea(Integer id) throws NotFoundException,BusinessException;
}
