package com.diytracker.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diytracker.app.entity.Project;
import com.diytracker.app.repository.CategoryRepository;
import com.diytracker.app.repository.ProjectRepository;
import com.diytracker.app.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<Project> getUserProjects(Long userId) {
		return projectRepository.getUserProjects(userId);
	}

	@Override
	public Project getProject(Long projectId) {
		return projectRepository.findById(projectId).get();
	}

	@Override
	public void updateProject(Project project) {
		projectRepository.save(project);
	}

	@Override
	public List<String> getCategoryNames() {
		return categoryRepository.getCategoryNames();
	}

}
