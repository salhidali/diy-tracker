package com.diytracker.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.diytracker.app.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query("select category.name from Category category")
	List<String> getCategoryNames();

}
