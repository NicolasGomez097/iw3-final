package com.iw3.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iw3.business.IUsuarioBusiness;
import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.model.Usuario;
import com.iw3.util.Constantes;

@RestController
@RequestMapping(Constantes.URL_USUARIO)
public class UsuarioRestController {
	
	@Autowired
	private IUsuarioBusiness usuarioBusiness;
	
	@PutMapping("")
	public ResponseEntity<String> modificarProyecto(@RequestBody Usuario usuario) {
		try {
			usuarioBusiness.updateUsuario(usuario);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (NotFoundException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error", e.getMessage());
			return new ResponseEntity<String>(responseHeaders,HttpStatus.NOT_FOUND);
		}
	}
	
}
