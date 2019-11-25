package com.iw3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw3.repository.BacklogRepository;
import com.iw3.repository.SprintRepository;
import com.iw3.repository.TareaRepository;
import com.iw3.repository.ToDoRepository;

@Service
public class ToDoController implements IToDoController{
	
	@Autowired
	private ToDoRepository repo;
	
}
