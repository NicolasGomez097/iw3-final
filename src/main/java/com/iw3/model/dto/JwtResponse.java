package com.iw3.model.dto;

public class JwtResponse{
	
	private String name;
	private String jwtToken;
	
	public JwtResponse(){}
	public JwtResponse(String username, String token) {
		this.setName(username);
		this.setJwtToken(token);
	}
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}	
}
