package com.iw3.business;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.ListaException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.model.Lista;
import com.iw3.model.Sprint;
import com.iw3.model.Tarea;
import com.iw3.repository.ListaRepository;
import com.iw3.repository.SprintRepository;
import com.iw3.repository.TareaRepository;

@Service
public class ListaBusiness implements IListaBusiness{
	
	@Autowired
	private ListaRepository repo;
	
	@Autowired
	private TareaRepository repoTareas;
	
	@Autowired
	private SprintRepository repoSprints;
	
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
	public void esValidoTipo(String lista)throws ListaException {
		if(
				lista.equals(Lista.BACKLOG) ||
				lista.equals(Lista.TODO) || 
				lista.equals(Lista.INPROGRESS) || 
				lista.equals(Lista.WATING) ||
				lista.equals(Lista.DONE)
			)
			return;
		
		throw new ListaException("El nombre solo puede ser: "+
				Lista.BACKLOG+", "+
				Lista.TODO+", "+
				Lista.INPROGRESS+", "+
				Lista.WATING+", "+
				Lista.DONE+".");
	}
	
	@Override
	public void esValido(Lista lista)throws ListaException {
		if(lista.getNombre() == null)
			throw new ListaException("No esta el campo nombre.");
		
		esValidoTipo(lista.getNombre());
		
		if(lista.getSprint() == null)
			throw new ListaException("No se tiene un sprint asignado.");
		if(lista.getSprint().getId() == null)
			throw new ListaException("El id del sprint no puede ser nulo");
	}
	

	@Override
	public void crearLista(Lista lista) throws BusinessException,ListaException{
		Optional<Sprint> sprint = repoSprints.findById(lista.getSprint().getId());
		if(!sprint.isPresent())
			throw new ListaException("No existe el sprint.");
		
		if(repo.findBySprintIdAndNombre(					
				lista.getSprint().getId(), lista.getNombre()
				).isPresent())
			throw new ListaException("Ya existe esta lista");
		
		try {			
			repo.save(lista);
			log.info("Se creo la lista "+lista.getJSON());
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}			
	}

	@Override
	public void moveToDo(Integer idTarea) throws NotFoundException, BusinessException,ListaException {
		Optional<Tarea> op;
		Tarea tarea;
		Sprint sprint;
		
		op = repoTareas.findById(idTarea);
		
		if(!op.isPresent())
			throw new NotFoundException("No existe la tarea con id="+idTarea);
		
		tarea = op.get();
		
		if(tarea.getEstimacion() == null)
			throw new ListaException("No esta la estimacion realizada para moverlo.");
		
		Lista lista = tarea.getLista();		
		if(!lista.getNombre().equals(Lista.BACKLOG))
			throw new ListaException("La terea no pertenece a la lista "+Lista.BACKLOG);
				
		sprint = lista.getSprint();
				
		try{
			moverTarea(lista, Lista.TODO, tarea, sprint);
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}
	}

	@Override
	public void moveInProgress(Integer idTarea) throws NotFoundException, BusinessException,ListaException {
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
			throw new ListaException("La terea no pertenece a la lista " + Lista.TODO 
					+ " o a la lista "+Lista.WATING);
				
		sprint = lista.getSprint();
				
		try{
			moverTarea(lista, Lista.INPROGRESS, tarea, sprint);
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}	
	}

	@Override
	public void moveWaiting(Integer idTarea) throws NotFoundException, BusinessException,ListaException {
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
			throw new ListaException("La terea no pertenece a la lista " + Lista.INPROGRESS 
					+ " o a la lista "+Lista.TODO);
				
		sprint = lista.getSprint();
				
		try{
			moverTarea(lista, Lista.WATING, tarea, sprint);
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}
	}

	@Override
	public void moveDone(Integer idTarea) throws NotFoundException, BusinessException,ListaException {
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
			throw new ListaException("La terea no pertenece a la lista " + Lista.INPROGRESS 
					+ " o a la lista "+Lista.WATING);
				
		sprint = lista.getSprint();
				
		try{
			moverTarea(lista, Lista.DONE, tarea, sprint);
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}	
	}
	
	private void removerTarea(List<Tarea> tareas, Integer idTarea){
		for(Tarea t:tareas) {
			if(t.getId() == idTarea) {
				tareas.remove(t);
				break;
			}
		}
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
		
		tarea.setUltimaModificacion(new Date());
		tarea.setLista(lista);
		lista.getTareas().add(tarea);
		repo.save(lista);
	}

	@Override
	public List<Tarea> getListaEspesificaOrder(Integer idSprint, String lista, String campo, String tipo)
			throws BusinessException, NotFoundException, ListaException {
		Optional<Lista> op;
		
		op = repo.findBySprintIdAndNombre(idSprint, lista);
		if (!op.isPresent())
			throw new NotFoundException("No se encuentra la lista con idSprint=" + idSprint);
		List<Tarea> tareas = op.get().getTareas();
		
		try {
			switch (campo) {
				case "prioridad":
					switch (tipo) {
						case "asc":
							tareas.sort(new Comparator<Tarea>() {
								@Override
								public int compare(Tarea sig, Tarea ant) {									
									return sig.getPrioridadNum().compareTo(ant.getPrioridadNum());
								}								
							});
							break;
						case "desc":
							tareas.sort(new Comparator<Tarea>() {
								@Override
								public int compare(Tarea sig, Tarea ant) {									
									return ant.getPrioridadNum().compareTo(sig.getPrioridadNum());
								}									
							});
							break;
						default:
							throw new ListaException("El la forma de ordenar debe ser 'asc' o 'desc'.");
					}
					break;
				case "fecha_creacion":
					switch(tipo) {
						case "asc":
							tareas.sort(new Comparator<Tarea>() {
								public int compare(Tarea sig, Tarea ant) {									
									return sig.getFechaCreacion().compareTo(ant.getFechaCreacion());
								}							
							});
							break;
						case "desc":
							tareas.sort(new Comparator<Tarea>() {
								@Override
								public int compare(Tarea sig, Tarea ant) {									
									return ant.getFechaCreacion().compareTo(sig.getFechaCreacion());
								}								
							});		
							break;
						default:
							throw new ListaException("El la forma de ordenar debe ser 'asc' o 'desc'.");
					}
					break;
	
				default:
					throw new ListaException("El campo no es valido, debe ser 'prioridad' o 'fecha_creacion'.");
			}
			
		}  catch (ListaException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}
		
		return op.get().getTareas();
	}
}
