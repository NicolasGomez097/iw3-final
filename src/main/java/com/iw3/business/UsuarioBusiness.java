package com.iw3.business;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.model.Usuario;
import com.iw3.repository.UsuarioRepository;


@Service
public class UsuarioBusiness implements IUsuarioBusiness {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UsuarioRepository usuarioDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Usuario load(String username) throws BusinessException, NotFoundException {
		List<Usuario> ou;
		try {
			ou=usuarioDAO.findByUsernameOrEmail(username, username);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if(ou.size()==0)
			throw new NotFoundException("No se encuentra el usuario con nombre o email "+username);
		return ou.get(0);
	}

	@Override
	public boolean isValid(String username,String password) throws BusinessException, NotFoundException {
		Optional<Usuario> op;
		boolean valid = false;
		
		if(username == null || password == null)
			return false;
		
		try {
			op = usuarioDAO.findByUsername(username);
			if(!op.isPresent())
				throw new NotFoundException("No se encuentra el usuario con username="+username);
			
			Usuario usuario = op.get();			
			valid = passwordEncoder.matches(password,usuario.getPassword());
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}
		return valid;
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) throws BusinessException, NotFoundException{
		 
		Optional<Usuario> op;
		
		try {
			op = usuarioDAO.findByUsername(usuario.getUsername());
			if(!op.isPresent())
				throw new NotFoundException("No se encuentra el usuario con username="+usuario.getUsername());
			
			Boolean samePassword = passwordEncoder.matches(usuario.getPassword(),op.get().getPassword());
			if(!samePassword) {
				op.get().setVersion(op.get().getVersion()+1);
				op.get().setPassword(passwordEncoder.encode(usuario.getPassword()));
			}

			usuarioDAO.save(op.get());
			log.info("Se actualizo el usuario "+usuario);
			return usuario;	
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e);
		}
	}

}
