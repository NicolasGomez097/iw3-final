package com.iw3.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenUtil implements Serializable {
	private static final long serialVersionUID = -2550185165626007488L;
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	private final String CLAIM_VERSION="version";
	
	@Value("${jwt.secret:secret-key}")
	private String secret;
	
	/**
	 * Devuelve el nombre de usuario a partir del token
	 * @param token Es el token del cual se extraera el usuario.
	 */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	/**
	 * Devuelve la fecha de expiracion del token
	 * @param token Es el token del cual se extraera la fecha.
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	/**
	 * Verificar que el token no este vencido.
	 * @param token Es el token que se verificara.
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	/**
	 * Generar un token para el usuario.
	 * @param userDetails Es el UserDetails del usuario.
	 */
	public String generateToken(String username, Integer version) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_VERSION, version);		
		return doGenerateToken(claims, username);
	}

	/**
	 * Devuelve la version del token.
	 * @param token Es el token del cual se extraera la version.
	 * @return La verion del token.
	 */
	public Integer getVersion(String token) {
		return (Integer)getAllClaimsFromToken(token).get(CLAIM_VERSION);
	}
	
	
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	/**
	 * Valida el token con un usuario y comprueba que no este expirado el token.
	 * @param token Es el token que se validara.
	 * @param username Es usuario que se validara con el token.
	 */
	public Boolean validateToken(String token, String username) {
		final String tokenUsername = getUsernameFromToken(token);
		return tokenUsername.equals(username) && !isTokenExpired(token);
	}
}