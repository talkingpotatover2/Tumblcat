package com.cat.investor;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;

import com.cat.account.entity.Account;
import com.cat.project.entity.Project;
import com.cat.reward.Reward;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Investor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long inId;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "project", referencedColumnName = "pId")
	private Project project;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "account", referencedColumnName = "aId")
	private Account account;
	
	@Column(nullable = false)
	private BigDecimal inPled;
	
	@CreatedDate
	private LocalDate inTs;
	
	@OneToOne
	@JoinColumn(nullable = false, name = "reward", referencedColumnName = "rwId")
	private Reward reward;
	
	@Column(nullable = false, length = 64)
	private String inPhone;
	
	@Column(nullable = false,columnDefinition = "TEXT")
	private String inAdd;
	
	@Column(nullable = false, length = 64)
	private String inPay;
	
	
}