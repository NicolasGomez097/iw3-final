package com.iw3.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.model.Tarea;
import com.iw3.repository.TareaRepository;

@Service
public class TareaController implements ITareaController{
	
	@Autowired
	private TareaRepository repo;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean esValido(Tarea tarea) {
		if(tarea.getNombre() == null)
			return false;
		if(tarea.getPrioridad() == null)
			return false;
		if(tarea.getLista() == null)
			return false;
		if(tarea.getEstimacion() == null)
			return false;
		return true;
	}

	@Override
	public List<Tarea> getLista() throws BusinessException {
		try {
			return repo.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}

	@Override
	public void crearTarea(Tarea tarea) throws BusinessException {
		try {			
			tarea.setFecha_creacion(new Date());
			tarea.setUltima_modificacion(new Date());
			repo.save(tarea);
			log.info("Se creo la tarea "+tarea.getJSON());
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}		
	}

	@Override
	public Tarea obtenerTarea(Integer id) throws NotFoundException, BusinessException {
		Optional<Tarea> op = null;
		try {
			op = repo.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if (!op.isPresent())
			throw new NotFoundException("No se encuentra la tarea con id=" + id);
		
		return op.get();
	}
	
}
