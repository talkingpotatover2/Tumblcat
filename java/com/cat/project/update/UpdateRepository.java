package com.cat.project.update;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cat.project.entity.ProjectUpdate;

public interface UpdateRepository extends JpaRepository<ProjectUpdate, Long> {

}
