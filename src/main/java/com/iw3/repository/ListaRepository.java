package com.iw3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iw3.model.Lista;

@Repository
public interface ListaRepository extends JpaRepository<Lista, Integer>{
	public Optional<Lista> findBySprintIdAndNombre(Integer idSprint,String nombre);
	public Optional<List<Lista>> findBySprintId(Integer idSprint);
	
}
