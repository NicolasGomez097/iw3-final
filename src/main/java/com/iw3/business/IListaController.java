package com.iw3.business;

import java.util.List;

import com.iw3.exeptions.AlreadyExistsException;
import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.ListaException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.model.Lista;
import com.iw3.model.Tarea;

public interface IListaController {
	public Lista getLista(Integer idLista) throws BusinessException,NotFoundException;
	public List<Lista> getListas(Integer idSprint) throws BusinessException,NotFoundException;
	public List<Tarea> getListaEspesifica(Integer idSprint,String lista) throws BusinessException,NotFoundException;
	public List<Tarea> getListaEspesificaOrder(Integer idSprint,String lista,String campo,String tipo) throws BusinessException,NotFoundException,ListaException;
	public void esValido(Lista lista) throws ListaException;
	public void esValidoTipo(String lista) throws ListaException;
	public void crearLista(Lista lista) throws BusinessException,AlreadyExistsException;
	public void moveToDo(Integer idTarea) throws NotFoundException,BusinessException,ListaException;
	public void moveInProgress(Integer idTarea) throws NotFoundException,BusinessException,ListaException;
	public void moveWaiting(Integer idTarea) throws NotFoundException,BusinessException,ListaException;
	public void moveDone(Integer idTarea) throws NotFoundException,BusinessException,ListaException;	
}
