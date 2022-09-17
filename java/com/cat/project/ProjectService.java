package com.cat.project;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cat.DataNotFoundException;
import com.cat.account.AccountRepository;
import com.cat.account.entity.Account;
import com.cat.project.entity.Project;
import com.cat.project.entity.ProjectStatus;
import com.cat.project.img.Image;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectService {
	private final ProjectRepository projectRepository;
	private final ProjectStatusRepository statusRepository;
	private final AccountRepository accountRepository;
	
	public ProjectStatus findPsId(String fname) {
		ProjectStatus psId = this.statusRepository.findByPsName(fname);
		return psId;
	}
	
	public ArrayList<Project> comparedDate(List<Project> projectList){
		ArrayList<Project> list = new ArrayList<>();
		LocalDate now = LocalDate.now();
		
		ProjectStatus start = findPsId("started");
		ProjectStatus end = findPsId("ended-successfully");
		
		for(Project p : projectList) {
			if(p.getPSdate().isBefore(now) || p.getPSdate().isEqual(now)) {
				//project시작
				if(p.getPEdate().isAfter(now) || p.getPSdate().isEqual(now)) {
					//진행중
					statusUpdate(start,p);
					list.add(p);
				}else {
					//project 마감
					statusUpdate(end,p);
				}
			}else {
				//project 오픈예정
			}
		}
		return list;
	}
	
	public List<Project> createdList(List<Project> projectList){
		ArrayList<Project> list = new ArrayList<>();
		ProjectStatus created = findPsId("created");
		for(Project p : projectList) {
			if(p.getProjectStatus().equals(created)) {
				list.add(p);
			}
		}
		return list;
	}
	
	public List<Project> getList(){
		return this.projectRepository.findAll();
	}
	
	public List<Project> getLikeList(){
		List<Project> list = new ArrayList<>();
		try {
			for(int i=0;i<7;i++) {
				if(this.projectRepository.findAllByLike().get(i) != null) {
					list.add(this.projectRepository.findAllByLike().get(i));
				}else {
					break;
				}
			}
		}catch(IndexOutOfBoundsException e) {
			return list;
		}
		return list;
	}
	
	public List<Project> searchkw(String kw){
		return this.projectRepository.findAllByKeyword(kw);
	}
	
	public List<Project> getCateList(String pCate){
		return this.projectRepository.findAllByCate(pCate);
	}
	
	public Project getProject(Long pId) {
		Optional<Project> project = this.projectRepository.findById(pId);
		if(project.isPresent()) {
			return project.get();
		}else {
			throw new DataNotFoundException("project not found");
		}
	}
	
	public void statusUpdate(ProjectStatus psId, Project project) {
		
		project.setProjectStatus(psId);
		this.projectRepository.save(project);
	}
	
	public void create(String pCate, String pName, String pDesc, 
			BigDecimal pGoal, LocalDate pSdate, LocalDate pEdate, 
			String pCreator, Image imgId, Account account, ProjectStatus psId)
	{
		Project p = new Project();
		p.setPCate(pCate);
		p.setPName(pName);
        p.setPDesc(pDesc);
        p.setPGoal(pGoal);
        p.setPSdate(pSdate);
        p.setPEdate(pEdate);
        p.setPCreator(pCreator);
        p.setImgIdR(imgId);
        p.setAccount(account);
        p.setProjectStatus(psId);
        this.projectRepository.save(p);
	}
	
	public void modify(
			Project p, 
			String pCate, 
			String pName, 
			String pDesc, 
			BigDecimal pGoal, 
			LocalDate pSdate, 
			LocalDate pEdate, 
			String pCreator) 
	{
		p.setPCate(pCate);
		p.setPName(pName);
        p.setPDesc(pDesc);
        p.setPGoal(pGoal);
        p.setPSdate(pSdate);
        p.setPEdate(pEdate);
        p.setPCreator(pCreator);
        this.projectRepository.save(p);
    }
	
	public void delete(Project project) {
		this.projectRepository.delete(project);
	}
	
	public void like(Project project, Account account) {
		project.getLiker().add(account);
		this.projectRepository.save(project);
	}
	
	public void notifyRequest(Project project, Account account) {
		project.getNotifyRequest().add(account);
		this.projectRepository.save(project);
		account.getNotifyRequest().add(project);
		this.accountRepository.save(account);
	}
	
	public void removeNotify(Project project, Account account) {
		project.getNotifyRequest().remove(account);
		this.projectRepository.save(project);
		account.getNotifyRequest().remove(project);
		this.accountRepository.save(account);
	}
}
