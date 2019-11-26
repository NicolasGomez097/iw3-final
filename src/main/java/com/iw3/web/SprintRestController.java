package com.iw3.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iw3.business.ISprintController;
import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.SprintException;
import com.iw3.model.Sprint;
import com.iw3.util.Constantes;

@RestController
@RequestMapping(Constantes.URL_SPRINT)
public class SprintRestController {
	
	@Autowired
	private ISprintController sprintController;
	
	@PostMapping("")
	public ResponseEntity<String> crearSpring(@RequestBody Sprint sprint){
		try{
			sprintController.esValido(sprint);
		}catch (SprintException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
			
		
		try {
			sprintController.crearSprint(sprint);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("")
	public ResponseEntity<List<Sprint>> getList(){
		try {			
			return new ResponseEntity<List<Sprint>>(sprintController.getLista(),HttpStatus.OK);
		}catch (BusinessException e) {
			e.printStackTrace();
			return new ResponseEntity<List<Sprint>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
