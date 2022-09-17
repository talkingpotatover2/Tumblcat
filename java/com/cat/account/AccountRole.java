package com.cat.account;

import lombok.Getter;

@Getter
public enum AccountRole {
	ADMIN("ROLE_ADMIN"),
	MEMBER("ROLE_MEMBER");
	
	AccountRole(String value){
		this.value = value;
	}
	
	private String value;
}
