package com.diytracker.app.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.diytracker.app.entity.Category;
import com.diytracker.app.util.AbstractRepositoryTest;
import com.github.springtestdbunit.annotation.DatabaseSetup;


public class CategoryRepositoryTest extends AbstractRepositoryTest {

	@Autowired
	CategoryRepository categoryRepository;
	
	
	@Test
	@DatabaseSetup(value = { "classpath:dataset/initialisation.xml" })
	public void testfindCategory() { 
		Optional<Category> object = categoryRepository.findById(1L);
		Category category = object.get();
		assertEquals("Electronics", category.getName());
	}
	
	@Test
	@DatabaseSetup(value = { "classpath:dataset/initialisation.xml" })
	public void testGetAllCategories() { 
		List<Category> category = categoryRepository.findAll();
		assertEquals(4, category.size());
	}
	
	@Test
	@DatabaseSetup(value = { "classpath:dataset/initialisation.xml" })
	public void testSaveCategory() {
		Category category = new Category();
		category.setId(444L);
		category.setName("3D printing");
		
		categoryRepository.save(category);
		
		List<Category> categories = categoryRepository.findAll();
		assertEquals(5, categories.size());
	}
}
