package com.iw3.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Proyecto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private Date fecha_creacion;
	
	@OneToMany(mappedBy = "proyecto", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Sprint> proyectos;
	
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
	
	public List<Sprint> getProyectos() {
		return proyectos;
	}
	public void setProyectos(List<Sprint> proyectos) {
		this.proyectos = proyectos;
	}
	public Date getFecha_creacion() {
		return fecha_creacion;
	}
	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
}
