package com.cat.investor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cat.account.entity.Account;
import com.cat.project.entity.Project;

public interface InvestorRepository extends JpaRepository<Investor, Long> {

	@Query("select i from Investor i where account = :account")
	List<Investor> findAllByAccount(@Param("account") Account account);

	@Query("select i from Investor i where project = :project")
	List<Investor> findAllByProject(@Param("project") Project project);

}
