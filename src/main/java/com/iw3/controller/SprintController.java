package com.iw3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw3.repository.BacklogRepository;
import com.iw3.repository.SprintRepository;
import com.iw3.repository.TareaRepository;

@Service
public class SprintController implements ISprintController{
	
	@Autowired
	private SprintRepository repo;
	
}
