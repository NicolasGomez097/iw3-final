package com.iw3.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Done {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
}
