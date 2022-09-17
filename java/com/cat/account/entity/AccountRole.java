package com.cat.account.entity;

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
public class AccountRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long arId;
	
	@Column(nullable = false, length = 20)
	private String arName;
}