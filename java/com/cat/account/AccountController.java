package com.cat.account;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cat.account.entity.Account;
import com.cat.project.ProjectService;
import com.cat.project.entity.Project;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/account")
@Controller
public class AccountController {

	private final AccountService accountService;
	private final ProjectService projectService;
	
	@RequestMapping("/signin")
	public String login() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup(AccountCreateForm accountCreateForm) {
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid AccountCreateForm accountCreateForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "signup";
		}
		
		if(!accountCreateForm.getAPw1().equals(accountCreateForm.getAPw2())) {
			bindingResult.rejectValue("aPw2", "passwordIncorrect", "비밀번호 확인이 일치하지 않습니다.");
			return "signup";
		}
		
		if(!accountCreateForm.getAEmail1().equals(accountCreateForm.getAEmail2())) {
			bindingResult.rejectValue("aEmail2", "emailIncorrect", "이메일 확인이 일치하지 않습니다.");
			return "signup";
		}
		
		try {
			accountService.create(accountCreateForm.getAName(),
								  accountCreateForm.getAEmail1(),
								  accountCreateForm.getAPw1());
		}catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup";
		}catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup";
		}
		

		
		return "loginsuccess";
	}
	
	@RequestMapping("/resetpwd")
	public String resetpwd() {
		return "resetpassword";
	}
	
	@GetMapping("/findpwd")
	public String findpwd() {
		return "findpassword";
	}
	
	/********************마이페이지 모음********************/
	
	@RequestMapping("/myproject/{aEmail}")
	public String myproject(Model model, @PathVariable("aEmail") String aEmail) {
		List<Project> myProjectList = this.accountService.getAccount(aEmail).getProject();
		model.addAttribute("myProjectList", myProjectList);
		return "myproject";
	}
	
	@RequestMapping("/setaccount")
	public String setaccount() {
		return "setting_account_a";
	}
	
	@GetMapping("/setaccountForm")
	public String setaccount_form() {
		return "setting_account_b";
	}
	
	@RequestMapping("/setaddress")
	public String setaddress() {
		return "setting_address_a";
	}
	
	@GetMapping("/setaddressForm")
	public String setaddress_form() {
		return "setting_address_b";
	}
	
	@RequestMapping("/setnotice")
	public String setnotice() {
		return "setting_notice_a";
	}
	
	@GetMapping("/setnotice_form")
	public String setnotice_form() {
		return "setting_notice_b";
	}
	
	@RequestMapping("/setpaytype")
	public String setPaytype() {
		return "setting_paytype_a";
	}
	
	@GetMapping("/setpaytype_form")
	public String setPaytype_form() {
		return "setting_paytype_b";
	}
	
	@GetMapping("/profile/{email}")
	public String profile(Model model, @PathVariable("email") String email) {
		Account account = this.accountService.getAccount(email);
		model.addAttribute("user", account);
		return "profile";
	}
	/* 마이페이지 수정 */
	@GetMapping("/profile/update/{email}")
	public String updateprofilename(
				@PathVariable("email") String email,
				@RequestParam(value = "profile_name", defaultValue = "") String aName,
				@RequestParam(value = "profile_desc", defaultValue = "") String aDesc
			) {
			if(aDesc.isEmpty() && !aName.isEmpty()) {
				//이름을 바꾼다
				this.accountService.profileUpdate(email, aName, 1);
			}else if(aName.isEmpty() && !aDesc.isEmpty()) {
				//설명을 바꾼다
				this.accountService.profileUpdate(email, aDesc, 2);
			}

			return String.format("redirect:/account/profile/%s", email);
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/notice")
	public String notice(Principal principal,Model model) {
		Account account = this.accountService.getAccount(principal.getName());
		Set<Project> project = account.getNotifyRequest();
		List<Project> projectList = new ArrayList<Project>();
		List<Long> date = new ArrayList<Long>();
		
		for(Project p : project) {
			if(p.getPSdate().isEqual(LocalDate.now())){
				projectList.add(p);
				Long end = p.getPSdate().until(LocalDate.now(), ChronoUnit.DAYS);
				date.add(end);
			}
		}
		model.addAttribute("date", date);
		model.addAttribute("project", projectList);
		
		return "notice";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/notice/remove/{pId}")
	public String noticeremove(Principal principal,@PathVariable("pId") Long pId,Model model) {
		
        Account account = this.accountService.getAccount(principal.getName());
        Project project = this.projectService.getProject(pId);

        this.projectService.removeNotify(project, account);
 
        return "redirect:/account/notice";

	}
	
	//회원탈퇴
		@GetMapping("/leave/{aEmail}")
		public String leave(Model model, @PathVariable("aEmail") String aEmail) {
			this.accountService.leave(aEmail);
			
			return "redirect:/account/signout";
		}
}
