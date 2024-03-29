package com.iw3.filtros;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iw3.business.UsuarioBusiness;
import com.iw3.util.JwtTokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private UsuarioBusiness usuarioBO ;
	
	public JWTAuthenticationFilter(
			UserDetailsService userDetailsService,
			JwtTokenUtil jwtTokenUtil, UsuarioBusiness usuarioBO) {
		
		super();
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.usuarioBO = usuarioBO;
	}
		
	private JwtTokenUtil jwtTokenUtil;
	
	private UserDetailsService userDetailsService;
	
	public static String AUTH_JWT_HEADER= "JWT-HEADER";
	public static String AUTH_JWT_PARAMETER= "jwt";
		
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		String username = null;
		String jwtToken = request.getHeader(AUTH_JWT_HEADER);		
		
		if(jwtToken == null)
			jwtToken = request.getParameter(AUTH_JWT_PARAMETER);
			
				
		if (jwtToken != null) {
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				Integer verToken = jwtTokenUtil.getVersion(jwtToken);
				Integer verUser = usuarioBO.load(username).getVersion();
				if(!verToken.equals(verUser)) {
					log.error("El token JWT es de una version antigua.");
					chain.doFilter(request, response);
					return;
				}
			} catch (IllegalArgumentException e) {
				log.error("No se pudo obtener el token.");
				chain.doFilter(request, response);
				return;
			} catch (ExpiredJwtException e) {
				log.error("El token JWT expiro");
				chain.doFilter(request, response);
				return;
			}
			catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage());
			}
		} 
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			if (jwtTokenUtil.validateToken(jwtToken, username)) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities()
						);
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				chain.doFilter(request, response);
				return;
			}
			
		}
		chain.doFilter(request, response);
	}


}