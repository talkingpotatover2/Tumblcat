package com.cat.project;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cat.project.entity.ProjectStatus;

public interface ProjectStatusRepository extends JpaRepository<ProjectStatus,Long> {
	
	ProjectStatus findByPsName(String status);
	
}
