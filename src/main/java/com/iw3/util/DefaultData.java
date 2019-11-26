package com.iw3.util;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iw3.model.Rol;
import com.iw3.model.Usuario;
import com.iw3.repository.RolRepository;
import com.iw3.repository.UsuarioRepository;

@Service
public class DefaultData {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UsuarioRepository usuarioDAO;

	@Autowired
	private RolRepository rolDAO;

	public Usuario ensureUserAdmin() {
		Optional<Usuario> op = usuarioDAO.findByUsername("admin");
		if (!op.isPresent()) {
			Usuario r = new Usuario();
			r.setUsername("admin");
			r.setFirstName("Administrador");
			r.setLastName("Administrador");
			r.setEmail("admin@alumnos.iua.edu.ar");
			r.setPassword(passwordEncoder.encode("admin"));
			r.setEnabled(true);
			r.setAccountNonExpired(true);
			r.setAccountNonLocked(true);
			r.setCredentialsNonExpired(true);
			Set<Rol> roles=new HashSet<Rol>();
			roles.add(ensureRoleAdmin());
			roles.add(ensureRoleUser());
			r.setRoles(roles);
			return usuarioDAO.save(r);
		} else {
			return op.get();
		}
	}

	public Rol ensureRoleAdmin() {
		return ensureRol("ROLE_ADMIN");
	}

	public Rol ensureRoleUser() {
		return ensureRol("ROLE_USER");
	}

	private Rol ensureRol(String role) {
		List<Rol> l = rolDAO.findByRol(role);
		if (l.size() == 0) {
			Rol r = new Rol();
			r.setRol(role);
			return rolDAO.save(r);
		} else {
			return l.get(0);
		}
	}
}
