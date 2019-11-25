package com.iw3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iw3.model.Backlog;

@Repository
public interface BacklogRepository extends JpaRepository<Backlog, Integer>{
}
