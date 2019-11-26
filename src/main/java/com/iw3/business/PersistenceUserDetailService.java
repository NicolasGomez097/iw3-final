package com.iw3.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.model.Usuario;

@Service
public class PersistenceUserDetailService implements UserDetailsService {
	
	@Autowired
	private IUsuarioBusiness usuarioService;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		Usuario u;
		try {
			u = usuarioService.load(username);
		} catch (BusinessException e) {
			log.error(e.getMessage(),e);
			throw new RuntimeException();
		} catch (NotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
		
		return u;
	}

}
