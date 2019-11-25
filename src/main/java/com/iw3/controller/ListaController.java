package com.iw3.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.model.Lista;
import com.iw3.model.Sprint;
import com.iw3.model.Tarea;
import com.iw3.repository.ListaRepository;
import com.iw3.repository.TareaRepository;

@Service
public class ListaController implements IListaController{
	
	@Autowired
	private ListaRepository repo;
	
	@Autowired
	private TareaRepository repoTareas;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	
	
	@Override
	public Lista getLista(Integer idLista) throws BusinessException, NotFoundException {
		Optional<Lista> op = null;
		try {
			op = repo.findById(idLista);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if (!op.isPresent())
			throw new NotFoundException("No se encuentra la lista con id=" + idLista);
		
		return op.get();
	}

	@Override
	public List<Lista> getListas(Integer idSprint) throws BusinessException,NotFoundException{
		Optional<List<Lista>> op;
		try {
			op = repo.findBySprintId(idSprint);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}
		if (!op.isPresent())
			throw new NotFoundException("No se encuentra las listas con idSprint=" + idSprint);
		
		return op.get();
	}
	
	@Override
	public List<Tarea> getListaEspesifica(Integer idSprint,String lista) throws BusinessException,NotFoundException{
		Optional<Lista> op;
		try {
			op = repo.findBySprintIdAndNombre(idSprint, lista);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}
		if (!op.isPresent())
			throw new NotFoundException("No se encuentra la lista con idSprint=" + idSprint);
		
		return op.get().getTareas();
	}

	@Override
	public boolean esValidoTipo(String lista) {
		if(lista.equals(Lista.BACKLOG) ||
				lista.equals(Lista.TODO) || 
				lista.equals(Lista.INPROGRESS) || 
				lista.equals(Lista.WATING) ||
				lista.equals(Lista.DONE))
			return true;
		return false;
	}
	
	@Override
	public boolean esValido(Lista lista) {
		if(!esValidoTipo(lista.getNombre()))
			return false;
		
		if(lista.getSprint() == null)
			return false;
		return true;
	}
	

	@Override
	public void crearLista(Lista lista) throws BusinessException {
		try {			
			repo.save(lista);
			log.info("Se creo la lista "+lista.getJSON());
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}			
	}

	@Override
	public boolean moveToDo(Integer idTarea) throws NotFoundException, BusinessException {
		Optional<Tarea> op;
		Tarea tarea;
		Sprint sprint;
		
		op = repoTareas.findById(idTarea);
		
		if(!op.isPresent())
			throw new NotFoundException("No existe la tarea con id="+idTarea);
		
		tarea = op.get();
		Lista lista = tarea.getLista();
		
		if(!lista.getNombre().equals(Lista.BACKLOG))
			return false;
				
		sprint = lista.getSprint();
				
		try{
			moverTarea(lista, Lista.TODO, tarea, sprint);
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}
		
		return true;
	}

	@Override
	public boolean moveInProgress(Integer idTarea) throws NotFoundException, BusinessException {
		Optional<Tarea> op;
		Tarea tarea;
		Sprint sprint;
		
		op = repoTareas.findById(idTarea);
		
		if(!op.isPresent())
			throw new NotFoundException("No existe la tarea con id="+idTarea);
		
		tarea = op.get();
		Lista lista = tarea.getLista();
		
		if(!lista.getNombre().equals(Lista.TODO) &&
				!lista.getNombre().equals(Lista.WATING))
			return false;
				
		sprint = lista.getSprint();
				
		try{
			moverTarea(lista, Lista.INPROGRESS, tarea, sprint);
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}
		
		return true;		
	}

	@Override
	public boolean moveWaiting(Integer idTarea) throws NotFoundException, BusinessException {
		Optional<Tarea> op;
		Tarea tarea;
		Sprint sprint;
		
		op = repoTareas.findById(idTarea);
		
		if(!op.isPresent())
			throw new NotFoundException("No existe la tarea con id="+idTarea);
		
		tarea = op.get();
		Lista lista = tarea.getLista();
		
		if(!lista.getNombre().equals(Lista.INPROGRESS) &&
				!lista.getNombre().equals(Lista.TODO))
			return false;
				
		sprint = lista.getSprint();
				
		try{
			moverTarea(lista, Lista.WATING, tarea, sprint);
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}
		
		return true;	
	}

	@Override
	public boolean moveDone(Integer idTarea) throws NotFoundException, BusinessException {
		Optional<Tarea> op;
		Tarea tarea;
		Sprint sprint;
		
		op = repoTareas.findById(idTarea);
		
		if(!op.isPresent())
			throw new NotFoundException("No existe la tarea con id="+idTarea);
		
		tarea = op.get();
		Lista lista = tarea.getLista();
		
		if(!lista.getNombre().equals(Lista.INPROGRESS) &&
				!lista.getNombre().equals(Lista.WATING))
			return false;
				
		sprint = lista.getSprint();
				
		try{
			moverTarea(lista, Lista.DONE, tarea, sprint);
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}
		
		return true;	
	}
	
	private List<Tarea> removerTarea(List<Tarea> tareas, Integer idTarea){
		for(Tarea t:tareas) {
			if(t.getId() == idTarea) {
				tareas.remove(t);
				break;
			}
		}
		return tareas;
	}
	
	private void moverTarea(Lista deLista,String aLista,Tarea tarea,Sprint sprint) throws Exception{
		removerTarea(deLista.getTareas(),tarea.getId());
		repo.save(deLista);
		
		Optional<Lista> op = repo.findBySprintIdAndNombre(sprint.getId(), aLista);
		Lista lista;
		
		if(!op.isPresent()) {
			lista = new Lista();
			lista.setNombre(aLista);
			lista.setSprint(sprint);
			lista.setTareas(new ArrayList<Tarea>());
		}else {
			lista = op.get();
		}
		
		tarea.setUltima_modificacion(new Date());
		tarea.setLista(lista);
		lista.getTareas().add(tarea);
		repo.save(lista);
	}
}
