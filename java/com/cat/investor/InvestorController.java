package com.cat.investor;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cat.account.AccountService;
import com.cat.account.entity.Account;
import com.cat.project.entity.Project;
import com.cat.reward.Reward;
import com.cat.reward.RewardService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/project/invest")
@RequiredArgsConstructor
@Controller
public class InvestorController {
	private final RewardService rewardService;
	private final AccountService accountService;
	private final InvestorService investorService;
	
	@GetMapping("/{rwId}")
	public String invest(Model model, @PathVariable("rwId") Long rwId,
			InvestorForm investorForm) {
		Reward reward = this.rewardService.getReward(rwId);
		model.addAttribute("reward", reward);
		return "project_invest";
	}
	
	@PostMapping("/{rwId}")
	public String invest(
			@Valid InvestorForm investorForm, BindingResult bindingResult,
			Model model, @PathVariable("rwId") Long rwId, Principal principal
	) {
		Reward reward = this.rewardService.getReward(rwId);
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("reward", reward);
			return "project_invest";
		}
		
		Account account = this.accountService.getAccount(principal.getName());
		Project project = reward.getProject();
			
		// TODO 질문을 저장한다.
		this.investorService.create(
			reward.getRwMin(),
			LocalDate.now(),
			investorForm.getInPhone(),
			investorForm.getInAdd(),
			investorForm.getInPay(),
			project,
			account,
			reward
		);
		return "redirect:/project/invest/complete";
		//return String.format("redirect:/project/detail/%s", rwId); // 질문 저장후 질문목록으로 이동
	}
	
	@RequestMapping("/pledged")
	public String pledged(Model model, Principal principal) {
		List<Investor> investorList;
		Account account = this.accountService.getAccount(principal.getName());
		investorList = this.investorService.getList(account);

		model.addAttribute("investorList", investorList);
		return "pledged";
	}
	
	@RequestMapping("/complete")
	public String complete() {
		return "complete";
	}
	
}