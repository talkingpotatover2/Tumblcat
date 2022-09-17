package com.cat.project;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.cat.account.AccountService;
import com.cat.account.entity.Account;
import com.cat.investor.Investor;
import com.cat.investor.InvestorService;
import com.cat.project.entity.Project;
import com.cat.project.entity.ProjectStatus;
import com.cat.project.img.Image;
import com.cat.project.img.ImageService;
import com.cat.reward.Reward;
import com.cat.reward.RewardService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/project")
@RequiredArgsConstructor
@Controller
public class ProjectController {
	private final ProjectService projectService;
	private final ImageService imageService;
	private final AccountService accountService;
	private final InvestorService investorService;
	private final RewardService rewardService;
	
	//db와 연결해주는 레퍼지토리를 가져와서 list를 조회
	//중간에 service 클래스를 추가해서 레퍼지토리에 직접 접근할 수 없도록 막아줌
	//model 클래스를 이용해서 가져온 list를 템플릿(html)에 전달 
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(value = "kw", defaultValue = "") String kw) {
		List<Project> projectList;
		if(kw != null) {
			projectList= this.projectService.searchkw(kw);
		}else {
			projectList = this.projectService.getList();
		}
		List<Project> likeitem = this.projectService.getLikeList();
		List<Project> gooditem = this.projectService.searchkw("필수");
		List<Project> killingTime = this.projectService.searchkw("심심");
		ArrayList<Project> list = this.projectService.comparedDate(projectList);
		List<Project> createdList = this.projectService.createdList(projectList);
		

    	// 오픈예정과 공개된 프로젝트 구분하기
		model.addAttribute("likeitemList", likeitem);
		model.addAttribute("killingTime", killingTime);
		model.addAttribute("gooditem", gooditem);
		model.addAttribute("projectList", list);
		model.addAttribute("createdList", createdList);

		return "project_list";
	}
	
	@RequestMapping("/list/{pCate}")
	public String listCate(Model model, @PathVariable("pCate") String pCate) {
		List<Project> projectList = this.projectService.getCateList(pCate);
		
		ArrayList<Project> list = this.projectService.comparedDate(projectList);
		List<Project> createdList = this.projectService.createdList(projectList);
    	
		model.addAttribute("projectList", list);
		model.addAttribute("createdList", createdList);
		model.addAttribute("categorypage","categoryp");
		return "category";
	}
	
	@RequestMapping("/prelaunching")
	public String prelaunching(Model model,@RequestParam(value = "kw", defaultValue = "") String kw) {
		List<Project> projectList;
		if(!kw.isEmpty()) {
			projectList= this.projectService.searchkw(kw);
		}else {
			projectList = this.projectService.getList();
		}
		
		List<Project> createdList = this.projectService.createdList(projectList);

		model.addAttribute("createdList", createdList);
		return "project_prelaunch";
	}
	
	//수정하고 있는 부분
	@RequestMapping(value = "/detail/{pId}")
	public String detail(Model model, @PathVariable("pId") Long pId,@RequestParam(value = "ref", defaultValue = "") String ref) {
		LocalDate now = LocalDate.now();
		
		Project project = this.projectService.getProject(pId);
		List<Investor> investorList = this.investorService.getListByProject(project);
		List<Reward> rewardList = this.rewardService.getRewardList(project);
		
		Long start = project.getPSdate().until(now, ChronoUnit.DAYS);
		Long end = now.until(project.getPEdate(), ChronoUnit.DAYS);
		LocalDate payDate = project.getPEdate().plusDays(1);
		
		BigDecimal rwMin = new BigDecimal("1000.00");;
		BigDecimal rwMax = new BigDecimal("0");
		
		model.addAttribute("project", project);
		//오픈예정 페이지는 따로 만들기
		if(!ref.isEmpty()) {
			model.addAttribute("start", start);
			return "project_prelaunch_detail";
		}else {
			//오픈 한 프로젝트
			model.addAttribute("end",end);
			model.addAttribute("payDate",payDate);
			model.addAttribute("investorList", investorList);
			
			if(rewardList.size() == 0) {
				this.rewardService.create(
						"선물 없이 후원하기",
						"",
						rwMin,
						rwMax,
						project
				);
			}
			
			return "project_detail";
		}
		
	}
	
	//CREATE
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String projectCreate(ProjectForm projectForm) {
		
		return "project_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
    public String projectCreate(
    		@RequestPart MultipartFile file,
    		@Valid ProjectForm projectForm, 
    		BindingResult bindingResult, 
    		Principal principal
    	    ) throws IOException{
		if (bindingResult.hasErrors()) {
            return "project_form";
		}
		
		String fileurl = imageService.uploadfile(file);
		String storefile = this.imageService.storedfile(file.getOriginalFilename());
		
		Account account = this.accountService.getAccount(principal.getName());
		
		if(projectForm.getImgDesc() == null) {
			projectForm.setImgDesc(" ");
		}
		this.imageService.filesave(file.getOriginalFilename(),storefile,fileurl, projectForm.getImgDesc());
		Image image = this.imageService.findImgid(storefile);
		
		ProjectStatus PsId =this.projectService.findPsId("created");
        // TODO 질문을 저장한다.
		this.projectService.create(
				projectForm.getPCate(),
				projectForm.getPName(),
				projectForm.getPDesc(), 
				projectForm.getPGoal(),
				projectForm.getPSdate(),
				projectForm.getPEdate(),
				projectForm.getPCreator(),
				image,
				account,
				PsId
		);
		
		//return "reward_form";
        return "redirect:/project/list"; // 질문 저장후 질문목록으로 이동
    }
	
	//MODIFY(수정UPDATE)
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{pId}")
    public String projectModify(ProjectForm projectForm, @PathVariable("pId") Long pId, Principal principal) {
		
        Project project = this.projectService.getProject(pId);
        if(!project.getAccount().getAEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        projectForm.setPCate(project.getPCate());
        projectForm.setPName(project.getPName());
        projectForm.setPDesc(project.getPDesc());
        projectForm.setPGoal(project.getPGoal());
        projectForm.setPSdate(project.getPSdate());
        projectForm.setPEdate(project.getPEdate());
        projectForm.setPCreator(project.getPCreator());
        return "project_form";
    }
	
	@PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{pId}")
    public String projectModify(@Valid ProjectForm projectForm, BindingResult bindingResult, 
            Principal principal, @PathVariable("pId") Long pId) {
        if (bindingResult.hasErrors()) {
            return "project_form";
        }

        Project project = this.projectService.getProject(pId);
        if (!project.getAccount().getAEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.projectService.modify(
        		project,
        		projectForm.getPCate(),
				projectForm.getPName(),
				projectForm.getPDesc(), 
				projectForm.getPGoal(),
				projectForm.getPSdate(),
				projectForm.getPEdate(),
				projectForm.getPCreator()
				);
        return String.format("redirect:/project/detail/%s", pId);
    }
	
	//DELETE
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{pId}")
    public String questionDelete(Principal principal, @PathVariable("pId") Long pId) {
        Project project = this.projectService.getProject(pId);
        if (!project.getAccount().getAEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.projectService.delete(project);
        return "redirect:/";
    }
	
	@GetMapping("/update/{pId}")
	public String projectUpdate(Model model, @PathVariable("pId") Long pId) {
		LocalDate now = LocalDate.now();
		
		Project project = this.projectService.getProject(pId);
		List<Investor> investorList = this.investorService.getListByProject(project);
		
		Long end = now.until(project.getPEdate(), ChronoUnit.DAYS);
		LocalDate payDate = project.getPEdate().plusDays(1);
		
		model.addAttribute("project", project);		
		model.addAttribute("end",end);
		model.addAttribute("payDate",payDate);
		model.addAttribute("investorList", investorList);
		
		return "project_update";
			
	}
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{pId}")
    public String projectLike(Principal principal, @PathVariable("pId") Long pId) {
        Project project = this.projectService.getProject(pId);
        Account account = this.accountService.getAccount(principal.getName());
        this.projectService.like(project, account);
        return String.format("redirect:/project/detail/%s", pId);
    }
	
	//알림설정
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/notification/request/{pId}")
    public String projectNotifyRequest(Principal principal, @PathVariable("pId") Long pId) {
        Project project = this.projectService.getProject(pId);
        Account account = this.accountService.getAccount(principal.getName());
        this.projectService.notifyRequest(project, account);
        
        return String.format("redirect:/project/detail/%s?ref=prelaunch", pId);
    }
	
	@RequestMapping("/category")
	public String category() {
		return "category";
	}
	
	@RequestMapping("/liked")
	public String liked() {
		return "liked";
	}
	
	
}