package com.iw3.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Sprint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	
	@OneToMany(mappedBy = "sprint")
	@JsonIgnore
	private List<Lista> listas;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@JsonIgnore
	public String getJSON() {
		//return new Gson().toJson(this,Sprint.class);
		return "";
	}
	public List<Lista> getListas() {
		return listas;
	}
	public void setListas(List<Lista> listas) {
		this.listas = listas;
	}	
}