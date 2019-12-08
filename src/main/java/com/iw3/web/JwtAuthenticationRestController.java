package com.iw3.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iw3.business.IUsuarioBusiness;
import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.model.dto.JwtRequest;
import com.iw3.model.dto.JwtResponse;
import com.iw3.util.JwtTokenUtil;

@RestController
@CrossOrigin
public class JwtAuthenticationRestController {
		
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private IUsuarioBusiness usuarioBusiness;
		
	
	
	@PostMapping(value = "/loginJwt")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
		Boolean isValid;
		Integer version;
		
		try{
			isValid = usuarioBusiness.isValid(request.getName(), request.getPassword());
			version = usuarioBusiness.load(request.getName()).getVersion();
		}catch (NotFoundException e) {
			return new ResponseEntity<JwtResponse>(HttpStatus.UNAUTHORIZED);
		}catch (BusinessException e) {
			return new ResponseEntity<JwtResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		

		if(!isValid)
			return new ResponseEntity<JwtResponse>(HttpStatus.UNAUTHORIZED);
		
		final String token = jwtTokenUtil.generateToken(request.getName(),version);
		JwtResponse response = new JwtResponse(request.getName(), token);
		
		return new ResponseEntity<JwtResponse>(response,HttpStatus.OK);
	}
}