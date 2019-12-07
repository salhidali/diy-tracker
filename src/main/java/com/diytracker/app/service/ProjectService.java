package com.diytracker.app.service;

import java.util.List;

import com.diytracker.app.entity.Comment;
import com.diytracker.app.entity.Project;

public interface ProjectService {

	/**
	 * Return all user projects
	 * 
	 * @param userId
	 * 
	 * @return List of all user projects
	 */
	public List<Project> getUserProjects(Long userId);
	
	/**
	 * Return the project with the given id
	 * 
	 * @param projectId
	 * 
	 * @return Project
	 */
	public Project getProject(Long projectId);
	
	/**
	 * Add or update a project
	 * 
	 * @param project
	 */
	public void updateProject(Project project);
	
	/**
	 * Return all category names
	 * 
	 * @return String List of category names
	 */
	public List<String> getCategoryNames();
	
}
