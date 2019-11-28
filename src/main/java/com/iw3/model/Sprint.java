package com.iw3.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(
	    uniqueConstraints=
	        @UniqueConstraint(columnNames={"proyecto_id", "nombre"})
	)
public class Sprint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private Date fecha_inicio;
	
	@ManyToOne
	private Proyecto proyecto;
	
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
	public Proyecto getProyecto() {
		return proyecto;
	}
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	public Date getFecha_inicio() {
		return fecha_inicio;
	}
	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}	
}
