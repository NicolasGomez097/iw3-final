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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iw3.business.ISprintBusiness;
import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.SprintException;
import com.iw3.model.Sprint;
import com.iw3.util.Constantes;

@RestController
@RequestMapping(Constantes.URL_SPRINT)
public class SprintRestController {
	
	@Autowired
	private ISprintBusiness sprintBusiness;
	
	@PostMapping("")
	public ResponseEntity<String> crearSpring(@RequestBody Sprint sprint){
		try {
			sprintBusiness.esValido(sprint);
			sprintBusiness.crearSprint(sprint);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (SprintException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error", e.getMessage());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("")
	public ResponseEntity<List<Sprint>> getList(
			@RequestParam(required = false,name = "id_proyecto")Integer idProyecto){
		try {			
			if(idProyecto != null)
				return new ResponseEntity<List<Sprint>>(
						sprintBusiness.getListaProyeto(idProyecto),HttpStatus.OK);
			
			return new ResponseEntity<List<Sprint>>(sprintBusiness.getLista(),HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<List<Sprint>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
