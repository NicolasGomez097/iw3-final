package com.iw3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iw3.model.Sprint;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Integer>{
	public Optional<Sprint> findByProyectoIdAndNombre(Integer idProyecto,String nombre);
	public List<Sprint> findByProyectoId(Integer idProyecto);
}
