package com.diytracker.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diytracker.app.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
