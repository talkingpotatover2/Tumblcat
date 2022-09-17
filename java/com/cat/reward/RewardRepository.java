package com.cat.reward;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cat.project.entity.Project;

public interface RewardRepository extends JpaRepository<Reward, Long> {
	@Query("select r from Reward r where project = :project")
	List<Reward> findAllByProject(@Param("project") Project project);
}