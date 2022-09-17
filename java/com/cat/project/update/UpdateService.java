package com.cat.project.update;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.cat.project.entity.Project;
import com.cat.project.entity.ProjectUpdate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UpdateService {

	private final UpdateRepository updateRepository;
	
	public void create(Project project, String uText) {
		ProjectUpdate pUpdate = new ProjectUpdate();
		pUpdate.setUText(uText);
//		pUpdate.setUTs(LocalDateTime.now());
		pUpdate.setProject(project);
		this.updateRepository.save(pUpdate);
	}
}
