package com.cat.reward;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardForm {
	@NotEmpty(message="선물 이름은 필수항목입니다.")
    @Size(max=200)
    private String rwName;

    @NotEmpty(message="선물 소개는 필수항목입니다.")
    private String rwDesc;
    
    @NotNull(message="최소후원금은 필수항목입니다.")
    private BigDecimal rwMin;
    
    @NotNull(message="최대후원금은 필수항목입니다.")
    private BigDecimal rwMax;
}