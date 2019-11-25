package com.iw3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw3.repository.TareaRepository;

@Service
public class TareaController implements ITareaController{
	
	@Autowired
	private TareaRepository repo;
	
}
