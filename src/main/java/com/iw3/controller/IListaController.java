package com.iw3.controller;

import java.util.List;

import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.model.Lista;
import com.iw3.model.Tarea;

public interface IListaController {
	public Lista getLista(Integer idLista) throws BusinessException,NotFoundException;
	public List<Lista> getListas(Integer idSprint) throws BusinessException,NotFoundException;
	public List<Tarea> getListaEspesifica(Integer idSprint,String lista) throws BusinessException,NotFoundException;
	public boolean esValido(Lista lista);
	public boolean esValidoTipo(String lista);
	public void crearLista(Lista lista) throws BusinessException;
	public boolean moveToDo(Integer idTarea) throws NotFoundException,BusinessException;
	public boolean moveInProgress(Integer idTarea) throws NotFoundException,BusinessException;
	public boolean moveWaiting(Integer idTarea) throws NotFoundException,BusinessException;
	public boolean moveDone(Integer idTarea) throws NotFoundException,BusinessException;	
}
