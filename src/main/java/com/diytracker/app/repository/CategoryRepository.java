package com.diytracker.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diytracker.app.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
