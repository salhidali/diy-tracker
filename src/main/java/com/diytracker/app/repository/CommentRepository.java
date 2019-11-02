package com.diytracker.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diytracker.app.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
