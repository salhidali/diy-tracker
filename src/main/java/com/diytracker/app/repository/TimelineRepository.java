package com.diytracker.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diytracker.app.entity.Timeline;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {

}
