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

import com.iw3.business.IListaBusiness;
import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.ListaException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.model.Lista;
import com.iw3.model.Tarea;
import com.iw3.util.Constantes;

@RestController
@RequestMapping(Constantes.URL_LISTA)
public class ListaRestController {
		
	@Autowired
	private IListaBusiness listaBusiness;
	
	@PostMapping("")
	public ResponseEntity<String> crearLista(@RequestBody Lista lista){
		try {
			listaBusiness.esValido(lista);
			listaBusiness.crearLista(lista);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", Constantes.URL_TAREA + "/" + lista.getNombre());
			responseHeaders.set("id_lista", ""+lista.getId());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.CREATED);
		}catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (ListaException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error", e.getMessage());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("")
	public ResponseEntity<List<Lista>> getListas(@RequestParam("id_sprint")Integer idSprint){
		try {
			
			if(idSprint == null)
				return new ResponseEntity<List<Lista>>(HttpStatus.BAD_REQUEST);
			
			return new ResponseEntity<List<Lista>>(listaBusiness.getListas(idSprint),HttpStatus.OK);
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
			
			return new ResponseEntity<Lista>(listaBusiness.getLista(idLista),HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<Lista>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (NotFoundException e) {
			return new ResponseEntity<Lista>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{lista}")
	public ResponseEntity<List<Tarea>> getListEspesifica(@PathVariable String lista,@RequestParam("id_sprint")Integer idSprint){
		try {
			if(idSprint == null)
				return new ResponseEntity<List<Tarea>>(HttpStatus.BAD_REQUEST);
			
			listaBusiness.esValidoTipo(lista);			
			return new ResponseEntity<List<Tarea>>(listaBusiness.getListaEspesifica(idSprint,lista),HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<List<Tarea>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (NotFoundException e) {
			return new ResponseEntity<List<Tarea>>(HttpStatus.NOT_FOUND);
		}catch (ListaException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error",e.getMessage());
			return new ResponseEntity<List<Tarea>>(responseHeaders,HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{lista}/order/{campo}/{tipo}")
	public ResponseEntity<List<Tarea>> getListEspesificaOrder(
			@PathVariable("lista") String lista,@RequestParam("id_sprint")Integer idSprint,
			@PathVariable("campo") String campo,@PathVariable("tipo") String tipo){
		try {
			if(idSprint == null)
				return new ResponseEntity<List<Tarea>>(HttpStatus.BAD_REQUEST);
			
			listaBusiness.esValidoTipo(lista);			
			return new ResponseEntity<List<Tarea>>(listaBusiness.getListaEspesificaOrder(
					idSprint, lista, campo, tipo),HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<List<Tarea>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (NotFoundException e) {
			return new ResponseEntity<List<Tarea>>(HttpStatus.NOT_FOUND);
		}catch (ListaException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error",e.getMessage());
			return new ResponseEntity<List<Tarea>>(responseHeaders,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/move_todo/{id}")
	public ResponseEntity<String> moveToDo(@PathVariable("id")Integer idTarea){
		try {
			listaBusiness.moveToDo(idTarea);
				
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch (NotFoundException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error", e.getMessage());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.NOT_FOUND);
		}catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (ListaException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error", e.getMessage());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/move_in_progress/{id}")
	public ResponseEntity<String> moveInProgress(@PathVariable("id")Integer idTarea){
		try {
			listaBusiness.moveInProgress(idTarea);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch (NotFoundException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error", e.getMessage());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.NOT_FOUND);
		}catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (ListaException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error", e.getMessage());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/move_waiting/{id}")
	public ResponseEntity<String> moveWaitin(@PathVariable("id")Integer idTarea){
		try {
			listaBusiness.moveWaiting(idTarea);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch (NotFoundException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error", e.getMessage());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.NOT_FOUND);
		}catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (ListaException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error", e.getMessage());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/move_done/{id}")
	public ResponseEntity<String> moveDone(@PathVariable("id")Integer idTarea){
		try {
			listaBusiness.moveDone(idTarea);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch (NotFoundException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error", e.getMessage());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.NOT_FOUND);
		}catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (ListaException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error", e.getMessage());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.BAD_REQUEST);
		}
	}
}
