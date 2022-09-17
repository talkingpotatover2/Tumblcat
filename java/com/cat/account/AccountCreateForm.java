package com.cat.account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateForm {

	@Size(min = 3, max = 32)
    @NotEmpty(message = "이름은 필수항목입니다.")
    private String aName;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String aEmail1;
    
    @NotEmpty(message = "이메일 확인은 필수항목입니다.")
    @Email
    private String aEmail2;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String aPw1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String aPw2;
}
