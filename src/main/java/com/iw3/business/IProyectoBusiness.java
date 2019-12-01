package com.iw3.business;

import java.util.List;

import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.exeptions.ProyectoException;
import com.iw3.model.Proyecto;

public interface IProyectoBusiness {
	public void esValido(Proyecto proyecto)throws ProyectoException;
	public Proyecto crearProyecto(Proyecto proyecto) throws BusinessException,ProyectoException;
	public List<Proyecto> getLista() throws BusinessException;
	public Proyecto updateProyecto(Proyecto proyecto) throws BusinessException,NotFoundException, ProyectoException;
	public void remove(int idProyecto) throws BusinessException,NotFoundException, ProyectoException;
}
