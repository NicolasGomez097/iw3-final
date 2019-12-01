package com.iw3.business;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.ProyectoException;
import com.iw3.model.Proyecto;
import com.iw3.repository.ProyectoRepository;

@Service
public class ProyectoBusiness implements IProyectoBusiness{
	
	@Autowired
	private ProyectoRepository repo;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void esValido(Proyecto proyecto)throws ProyectoException {
		if(proyecto.getNombre() == null)
			throw new ProyectoException("No esta el campo nombre.");
		if(proyecto.getNombre().length() < 4)
			throw new ProyectoException("El nombre tiene menos de 4 letras");
	}

	@Override
	public void crearProyecto(Proyecto proyecto) throws BusinessException,ProyectoException {
		Optional<Proyecto> opProyecto = repo.findByNombre(proyecto.getNombre());
		if(opProyecto.isPresent())
			throw new ProyectoException("Ya existe este proyecto");
		
		try {	
			proyecto.setFecha_creacion(new Date());	
			repo.save(proyecto);
			log.info("Se creo el proyecto "+proyecto.getJSON());
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}			
	}
	
	@Override
	public List<Proyecto> getLista() throws BusinessException {
		try {
			return repo.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}	
}
