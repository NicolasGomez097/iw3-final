package com.iw3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iw3.model.ToDo;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Integer>{
}
