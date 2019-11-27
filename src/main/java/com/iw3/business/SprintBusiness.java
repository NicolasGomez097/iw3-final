package com.iw3.business;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.SprintException;
import com.iw3.model.Proyecto;
import com.iw3.model.Sprint;
import com.iw3.repository.ProyectoRepository;
import com.iw3.repository.SprintRepository;

@Service
public class SprintBusiness implements ISprintBusiness{
	
	@Autowired
	private SprintRepository repo;
	
	@Autowired
	private ProyectoRepository repoProyectos;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void esValido(Sprint sprint)throws SprintException {
		if(sprint.getNombre() == null)
			throw new SprintException("No esta el campo nombre.");
		if(sprint.getNombre().length() < 4)
			throw new SprintException("El nombre tiene menos de 4 letras");
		if(sprint.getProyecto() == null)
			throw new SprintException("El sprint debe estar asociado a un proyecto.");
	}

	@Override
	public void crearSprint(Sprint sprint) throws BusinessException,SprintException {
		Optional<Proyecto> proyecto = repoProyectos.findById(sprint.getProyecto().getId());
		if(!proyecto.isPresent())
			throw new SprintException("El proyecto no existe.");
		
		Optional<Sprint> aux = repo.findByProyectoIdAndNombre(
				sprint.getProyecto().getId(), sprint.getNombre());
		
		if(aux.isPresent())
			throw new SprintException("Ya existe este Sprint en este proyecto.");
		
		try {			
			repo.save(sprint);
			log.info("Se creo el sprint "+sprint.getJSON());
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}			
	}

	@Override
	public List<Sprint> getLista() throws BusinessException {
		try {
			return repo.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}	
}
