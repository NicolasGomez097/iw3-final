package com.iw3.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw3.exeptions.BusinessException;
import com.iw3.model.Sprint;
import com.iw3.repository.SprintRepository;

@Service
public class SprintController implements ISprintController{
	
	@Autowired
	private SprintRepository repo;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean esValido(Sprint sprint) {
		if(sprint.getNombre() == null)
			return false;
		return true;
	}

	@Override
	public void crearSprint(Sprint sprint) throws BusinessException {
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
