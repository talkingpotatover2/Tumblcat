package com.cat.investor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestorForm {
	@NotEmpty(message="연락처는 필수항목입니다.")
    @Size(max=200)
    private String inPhone;
    
    @NotEmpty(message="주소는 필수항목입니다.")
    private String inAdd;
    
    @NotEmpty(message="결제 수단은 필수항목입니다.")
    @Size(max=200)
    private String inPay;
}