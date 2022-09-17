package com.cat.account;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cat.DataNotFoundException;
import com.cat.account.entity.Account;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountService {

	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;
	
	public Account create(String aName, String aEmail, String aPw) {
		Account account = new Account();
		account.setAName(aName);
		account.setAEmail(aEmail);
		account.setAPw(passwordEncoder.encode(aPw));
		this.accountRepository.save(account);
		return account;
	}
	
	public Account getAccount(String aEmail) {
		Optional<Account> account = this.accountRepository.findByaEmail(aEmail);
		if(account.isPresent()) {
			return account.get();
		}else {
			throw new DataNotFoundException("account not found");
		}
	}
	
	public void profileUpdate(String email, String param, int t) {
		
		Optional<Account> accountOptional = this.accountRepository.findByaEmail(email);
		Account account = new Account();
		if(accountOptional.isPresent()) {
			account = accountOptional.get();
			if(t == 1) {
				//account name 변경
				account.setAName(param);
			}else {
				//account desc 변경
				account.setADesc(param);
			}
		}
		this.accountRepository.save(account);

	}
	
	public void leave(String aEmail) {
		Optional<Account> account = this.accountRepository.findByaEmail(aEmail);
		account.ifPresent(selectAccount ->{
			accountRepository.delete(selectAccount);
		});
	}
}
