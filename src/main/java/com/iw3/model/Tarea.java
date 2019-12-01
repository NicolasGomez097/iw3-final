package com.iw3.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tarea {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private Date fechaCreacion;
	private Date ultimaModificacion;
	private String prioridad;
	private Double estimacion;
	
	public final static String PRIORIDAD_BAJA="Baja";
	public final static String PRIORIDAD_MEDIA="Media";
	public final static String PRIORIDAD_ALTA="Alta";
	
	@JsonIgnore
	private Integer prioridadNum;
	
	@ManyToOne()
	@JsonBackReference
	private Lista lista;

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
	
	

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getUltimaModificacion() {
		return ultimaModificacion;
	}

	public void setUltimaModificacion(Date ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}

	public Integer getPrioridadNum() {
		return prioridadNum;
	}

	public void setPrioridadNum(Integer prioridadNum) {
		this.prioridadNum = prioridadNum;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
		switch (prioridad) {
		case PRIORIDAD_BAJA:
			this.prioridadNum = 1;
			break;
		case PRIORIDAD_MEDIA:
			this.prioridadNum = 2;	
			break;
		case PRIORIDAD_ALTA:
			this.prioridadNum = 3;
			break;
		}
	}

	public Lista getLista() {
		return lista;
	}

	public void setLista(Lista lista) {
		this.lista = lista;
	}	
		
	public Double getEstimacion() {
		return estimacion;
	}

	public void setEstimacion(Double estimacion) {
		this.estimacion = estimacion;
	}

	@JsonIgnore
	public String getJSON() {
		//return new Gson().toJson(this,Tarea.class);
		return "";
	}
}
