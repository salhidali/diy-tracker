package com.diytracker.app.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.diytracker.app.entity.Project;
import com.diytracker.app.repository.CategoryRepository;
import com.diytracker.app.repository.ProjectRepository;
import com.diytracker.app.service.impl.ProjectServiceImpl;
import com.diytracker.app.util.AbstractServiceTest;


public class ProjectServiceImplTest extends AbstractServiceTest {

	@Mock
	ProjectRepository projectRepository;
	
	@Mock
	CategoryRepository categoryRepository;
	
	@InjectMocks
	ProjectServiceImpl projectService;

	@Test
	public void testGetUserProjects() throws Exception {
		// Given
		Mockito.when(projectRepository.getUserProjects(Mockito.anyLong())).thenReturn(getProjects());
		//When
		List<Project> userProjects = projectService.getUserProjects(1L);
		//Then
		assertThat(userProjects).isNotNull();
		assertThat(userProjects.get(0).getTitle()).isEqualTo("Project 1");
	}

	@Test
	public void testGetProject() throws Exception {
		// Given
		Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getProjects().get(0)));
		//When
		Project project = projectService.getProject(1L);
		//Then
		assertThat(project).isNotNull();
		assertThat(project.getTitle()).isEqualTo("Project 1");
	}

	@Test
	public void testUpdateProject() throws Exception {
		// Given
		Mockito.when(projectRepository.save(Mockito.any())).thenReturn(getProjects().get(0));
		//When
		Project newProject = getProjects().get(0);
		projectService.updateProject(newProject);
		//Then
		
		ArgumentCaptor<Project> argumentCaptor = ArgumentCaptor.forClass(Project.class);
		Mockito.verify(projectRepository).save(argumentCaptor.capture());
		assertThat(argumentCaptor.getValue().getTitle()).isEqualTo("Project 1");
	}

	@Test
	public void testGetCategoryNames() throws Exception {
		// Given
		List<String> names = Arrays.asList("Category 1", "Category 2");
		Mockito.when(categoryRepository.getCategoryNames()).thenReturn(names);
		//When
		List serviceNames = projectService.getCategoryNames();
		//Then
		assertThat(serviceNames).isNotNull();
		assertThat(serviceNames.get(0)).isEqualTo("Category 1");
	}
	
	
	
	
}
