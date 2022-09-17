package com.cat.reward;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cat.account.entity.Account;
import com.cat.project.entity.Project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Reward {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long rwId;
	
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "project", referencedColumnName = "pId")
	private Project project;
	
	@Column(nullable = false)
	private BigDecimal rwMin;
	
	@Column(nullable = false)
	private BigDecimal rwMax;
	
	@Column(nullable = false, length = 64)
	private String rwName;
	
	@Column(nullable = false, columnDefinition = "TEXT") 
	private String rwDesc;
}