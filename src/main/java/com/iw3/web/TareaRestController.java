package com.iw3.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iw3.business.IListaController;
import com.iw3.business.ITareaController;
import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.exeptions.TareaException;
import com.iw3.model.Lista;
import com.iw3.model.Tarea;
import com.iw3.util.Constantes;

@RestController
@RequestMapping(Constantes.URL_TAREA)
public class TareaRestController {
	
	@Autowired
	private ITareaController tareaController;
	
	@Autowired
	private IListaController listaController;
	
	@GetMapping("")
	public ResponseEntity<List<Tarea>> getList(){
		try {
			return new ResponseEntity<List<Tarea>>(tareaController.getLista(),HttpStatus.OK);
		} catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<List<Tarea>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}	
	
	@GetMapping("/{id}")
	public ResponseEntity<Tarea> getTarea(@PathVariable("id") int idTarea){
		try {
			return new ResponseEntity<Tarea>(tareaController.obtenerTarea(idTarea),HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (NotFoundException e) {
			return new ResponseEntity<Tarea>(HttpStatus.NOT_FOUND);
		}catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<Tarea>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<String> crearTarea(@RequestBody Tarea tarea){
		try{
			tareaController.esValido(tarea);
		}catch (TareaException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);	
		}
		
		try {
			Lista lista = listaController.getLista(tarea.getLista().getId());
			if(!lista.getNombre().equals(Lista.BACKLOG))
				return new ResponseEntity<String>("Se debe crear asignado a la lista backlog",HttpStatus.BAD_REQUEST);
			
			tareaController.crearTarea(tarea);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (NotFoundException e) {
			return new ResponseEntity<String>("No existe la lista",HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("")
	public ResponseEntity<String> modificarTarea(@RequestBody Tarea tarea){
		try{
			tareaController.esValido(tarea);
		}catch (TareaException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);	
		}	
		
		try {
			Lista lista = listaController.getLista(tarea.getLista().getId());
			if(!lista.getNombre().equals(Lista.BACKLOG))
				return new ResponseEntity<String>("Solo se puede modificar la tarea en el backlog",HttpStatus.BAD_REQUEST);
			
			tareaController.updateTarea(tarea);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (NotFoundException e) {
			return new ResponseEntity<String>("No existe la lista",HttpStatus.BAD_REQUEST);
		}
	}
}
