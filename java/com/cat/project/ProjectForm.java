package com.cat.project;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectForm {
	@NotEmpty(message="프로젝트 이름은 필수항목입니다.")
    @Size(max=200)
    private String pName;

    @NotEmpty(message="프로젝트 소개는 필수항목입니다.")
    private String pDesc;
    
    @NotEmpty(message="카테고리는 필수항목입니다.")
    private String pCate;
    
    @NotNull(message="창작자 소개는 필수항목입니다.")
    private BigDecimal pGoal;
    
    @NotNull(message="시작일은 필수항목입니다.")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate pSdate;
    
    @NotNull(message="종료일은 필수항목입니다.")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate pEdate;
    
    @NotEmpty(message="창작자 소개는 필수항목입니다.")
    private String pCreator;
    
//    @NotEmpty(message="이미지 소개는 필수항목입니다.")
    private String ImgDesc;
}