package com.cat.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProjectStatus {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long psId;
	
	@Column(nullable = false, length = 64)
	private String psName;
}