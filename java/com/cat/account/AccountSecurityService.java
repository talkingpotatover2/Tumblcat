package com.cat.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cat.account.entity.Account;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountSecurityService implements UserDetailsService{
	
	private final AccountRepository accountRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Optional<Account> _account = this.accountRepository.findByaEmail(username);
		if(_account.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		Account account = _account.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		if("admin".equals(username)) {
			authorities.add(new SimpleGrantedAuthority(AccountRole.ADMIN.getValue()));
		}else {
			authorities.add(new SimpleGrantedAuthority(AccountRole.MEMBER.getValue()));
		}
		
		return new User(account.getAEmail(), account.getAPw(), authorities);
	}
}
