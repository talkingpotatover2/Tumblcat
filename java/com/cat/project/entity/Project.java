package com.cat.project.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.cat.account.entity.Account;
import com.cat.project.img.Image;
import com.cat.reward.Reward;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long pId;
	
	@Column(nullable = false, updatable = true, length = 64)
	private String pName;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String  pCreator;
	
    @Column(nullable = false, updatable = true, 
    		columnDefinition = "TEXT")
	private String pDesc;
    
    @Column(nullable = false, updatable = true, length = 64)
	private String pCate;

    @Column(nullable = false, updatable = true)
	private BigDecimal  pGoal;
	
//    @ColumnDefault("0") 
//    @Column(nullable = false, updatable = true)
//	private BigDecimal  pPled;

//    @ColumnDefault("0") 
//    @Column(nullable = false)
//	private Integer pIns;
    
    @ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST,CascadeType.MERGE})
    Set<Account> liker;
    
    @ManyToMany
    Set<Account> notifyRequest;
    
	private  LocalDate pSdate;
	
	private  LocalDate pEdate;
	
	
	@OneToOne
	@JoinColumn(nullable = false, name = "projectStatus",referencedColumnName = "psId")
	private ProjectStatus projectStatus; 
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<ProjectUpdate> projectUpdateList;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="Image",referencedColumnName = "imgId")
	private Image imgIdR;
	
	@ManyToOne
	private Account account;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<Reward> rewardList;
	
}