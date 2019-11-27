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

import com.iw3.business.ITareaBusiness;
import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.exeptions.TareaException;
import com.iw3.model.Tarea;
import com.iw3.util.Constantes;

@RestController
@RequestMapping(Constantes.URL_TAREA)
public class TareaRestController {
	
	@Autowired
	private ITareaBusiness tareaBusiness;
		
	@GetMapping("")
	public ResponseEntity<List<Tarea>> getList(){
		try {
			return new ResponseEntity<List<Tarea>>(tareaBusiness.getLista(),HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<List<Tarea>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}	
	
	@GetMapping("/{id}")
	public ResponseEntity<Tarea> getTarea(@PathVariable("id") int idTarea){
		try {
			return new ResponseEntity<Tarea>(tareaBusiness.obtenerTarea(idTarea),HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (NotFoundException e) {
			return new ResponseEntity<Tarea>(HttpStatus.NOT_FOUND);
		}catch (BusinessException e) {
			return new ResponseEntity<Tarea>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<String> crearTarea(@RequestBody Tarea tarea){
		try {		
			tareaBusiness.esValido(tarea);
			tareaBusiness.crearTarea(tarea);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (TareaException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);	
		}
	}
	
	@PutMapping("")
	public ResponseEntity<String> modificarTarea(@RequestBody Tarea tarea){
		try {
			tareaBusiness.esValido(tarea);
			tareaBusiness.updateTarea(tarea);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (TareaException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);	
		}catch (NotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
}
