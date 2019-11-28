package com.iw3.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iw3.business.IProyectoBusiness;
import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.ProyectoException;
import com.iw3.model.Proyecto;
import com.iw3.util.Constantes;

@RestController
@RequestMapping(Constantes.URL_PROYECTO)
public class ProyectoRestController {
	
	@Autowired
	private IProyectoBusiness proyectoBusiness;
	
	@PostMapping("")
	public ResponseEntity<String> crearProyecto(@RequestBody Proyecto proyecto){
		
		try {
			proyectoBusiness.esValido(proyecto);
			proyectoBusiness.crearProyecto(proyecto);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (ProyectoException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error", e.getMessage());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("")
	public ResponseEntity<List<Proyecto>> getList(){
		try {			
			return new ResponseEntity<List<Proyecto>>(proyectoBusiness.getLista(),HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<List<Proyecto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
