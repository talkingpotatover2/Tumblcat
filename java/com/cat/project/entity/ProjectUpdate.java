package com.cat.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProjectUpdate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long uId;
	
	@ManyToOne
	private Project project;
	
//	@ManyToOne
//	@JoinColumn(nullable = false, name = "account", referencedColumnName = "aId")
//	private Account account;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String uText;
	
//	@CreatedDate
//	private LocalDateTime uTs;
}