package com.diytracker.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diytracker.app.entity.Tool;

public interface ToolRepository extends JpaRepository<Tool, Long> {

}
