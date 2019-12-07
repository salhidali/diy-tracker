package com.diytracker.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.diytracker.app.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	@Query("select project from Project project where project.user.id = :#{#user}")
	List<Project> getUserProjects(@Param("user") Long userId);
}
