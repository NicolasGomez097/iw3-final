package com.iw3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iw3.model.Proyecto;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer>{
	Optional<Proyecto> findByNombre(String nombre);
}
