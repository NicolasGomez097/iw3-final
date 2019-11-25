package com.iw3.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iw3.controller.IListaController;
import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.model.Lista;
import com.iw3.model.Tarea;
import com.iw3.util.Constantes;

@RestController
@RequestMapping(Constantes.URL_LISTA)
public class ListaRestController {
		
	@Autowired
	private IListaController listaController;
	
	@PostMapping("")
	public ResponseEntity<String> crearLista(@RequestBody Lista lista){
		if(!listaController.esValido(lista))
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		try {
			listaController.crearLista(lista);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", Constantes.URL_TAREA + "/" + lista.getNombre());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.CREATED);
		}catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("")
	public ResponseEntity<List<Lista>> getListas(@RequestParam("id_sprint")Integer idSprint){
		try {
			
			if(idSprint == null)
				return new ResponseEntity<List<Lista>>(HttpStatus.BAD_REQUEST);
			
			return new ResponseEntity<List<Lista>>(listaController.getListas(idSprint),HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<List<Lista>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (NotFoundException e) {
			return new ResponseEntity<List<Lista>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("get/{id}")
	public ResponseEntity<Lista> getLista(@PathVariable("id")Integer idLista){
		try {
			
			if(idLista == null)
				return new ResponseEntity<Lista>(HttpStatus.BAD_REQUEST);
			
			return new ResponseEntity<Lista>(listaController.getLista(idLista),HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<Lista>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (NotFoundException e) {
			return new ResponseEntity<Lista>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{lista}")
	public ResponseEntity<List<Tarea>> getListEspesifica(@PathVariable String lista,@RequestParam("id_sprint")Integer idSprint){
		try {
			if(!listaController.esValidoTipo(lista))
				return new ResponseEntity<List<Tarea>>(HttpStatus.BAD_REQUEST);
			
			if(idSprint == null)
				return new ResponseEntity<List<Tarea>>(HttpStatus.BAD_REQUEST);
			
			return new ResponseEntity<List<Tarea>>(listaController.getListaEspesifica(idSprint,lista),HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<List<Tarea>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (NotFoundException e) {
			return new ResponseEntity<List<Tarea>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/move_todo/{id}")
	public ResponseEntity<String> moveToDo(@PathVariable("id")Integer idTarea){
		try {
			if(!listaController.moveToDo(idTarea))
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
				
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch (NotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/move_in_progress/{id}")
	public ResponseEntity<String> moveInProgress(@PathVariable("id")Integer idTarea){
		try {
			if(!listaController.moveInProgress(idTarea))
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch (NotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/move_waiting/{id}")
	public ResponseEntity<String> moveWaitin(@PathVariable("id")Integer idTarea){
		try {
			if(!listaController.moveWaiting(idTarea))
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch (NotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/move_done/{id}")
	public ResponseEntity<String> moveDone(@PathVariable("id")Integer idTarea){
		try {
			if(!listaController.moveDone(idTarea))
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch (NotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
