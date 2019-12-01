package com.iw3.business;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.exeptions.TareaException;
import com.iw3.model.Lista;
import com.iw3.model.Tarea;
import com.iw3.repository.ListaRepository;
import com.iw3.repository.TareaRepository;

@Service
public class TareaBusiness implements ITareaBusiness{
	
	@Autowired
	private TareaRepository repo;
	
	@Autowired
	private ListaRepository repoListas;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void esValido(Tarea tarea) throws TareaException{
		if(tarea.getNombre() == null)
			throw new TareaException("No tiene el campo nombre");
		if(tarea.getPrioridad() == null)
			throw new TareaException("No tiene el campo prioridad");
		if(tarea.getLista() == null)
			throw new TareaException("No tiene una lista asignada");
		if(!tarea.getPrioridad().equals(Tarea.PRIORIDAD_BAJA)&&
				!tarea.getPrioridad().equals(Tarea.PRIORIDAD_MEDIA)&&
				!tarea.getPrioridad().equals(Tarea.PRIORIDAD_ALTA))
			throw new TareaException("La prioridad no es valida ("+
				Tarea.PRIORIDAD_BAJA+","+
				Tarea.PRIORIDAD_MEDIA+","+
				Tarea.PRIORIDAD_ALTA+")");
		
		if(tarea.getNombre().length() < 4)
			throw new TareaException("El nombre debe contener almenos 4 letras");
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
	public Tarea crearTarea(Tarea tarea) throws BusinessException,TareaException {
		Optional<Lista> lista = repoListas.findById(tarea.getLista().getId());
		if(!lista.isPresent())
			throw new TareaException("No existe la lista.");
		
		if(!lista.get().getNombre().equals(Lista.BACKLOG))
			throw new TareaException("Solo se pueden crear tareas en la lista de backlog");
		
		try {			
			tarea.setFechaCreacion(new Date());
			tarea.setUltimaModificacion(new Date());
			repo.save(tarea);
			log.info("Se creo la tarea "+tarea.getJSON());
			return tarea;
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}		
	}
	
	@Override
	public Tarea updateTarea(Tarea tarea) throws BusinessException,TareaException,NotFoundException {
		Optional<Tarea> opTarea = repo.findById(tarea.getId());
		if(!opTarea.isPresent())
			throw new NotFoundException("No existe la tarea a modificar");
		
		if(!opTarea.get().getLista().getNombre().equals(Lista.BACKLOG))
			throw new TareaException("Solo se pueden modificar tareas en la lista de backlog");
		
		Optional<Lista> lista = repoListas.findById(tarea.getLista().getId());
		if(!lista.isPresent())
			throw new TareaException("No existe la lista.");
		
		if(!lista.get().getNombre().equals(Lista.BACKLOG))
			throw new TareaException("Solo se puede mover la lista con las acciones /listas/move");
				
		try {			
			tarea.setFechaCreacion(opTarea.get().getFechaCreacion());
			tarea.setUltimaModificacion(new Date());
			repo.save(tarea);
			log.info("Se actualizo la tarea "+tarea.getJSON());
			return tarea;
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
