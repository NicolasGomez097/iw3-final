package com.iw3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iw3.model.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer>{
}
