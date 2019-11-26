package com.iw3.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Lista {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nombre;
	
	@ManyToOne()
	private Sprint sprint;
	
	@OneToMany(mappedBy = "lista")
	@JsonManagedReference
	private List<Tarea> tareas;	
	
	public static String BACKLOG= "backlog";
	public static String TODO= "to_do";
	public static String INPROGRESS= "in_progress";
	public static String WATING= "waiting";
	public static String DONE= "done";
	
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
	public Sprint getSprint() {
		return sprint;
	}
	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}
	public List<Tarea> getTareas() {
		return tareas;
	}
	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}
	
	@JsonIgnore
	public String getJSON() {
		//return new Gson().toJson(this,Lista.class);
		return "";
	}
}
