package com.diytracker.app.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.diytracker.app.entity.Tool;
import com.diytracker.app.util.AbstractRepositoryTest;
import com.github.springtestdbunit.annotation.DatabaseSetup;


public class ToolRepositoryTest extends AbstractRepositoryTest {

	@Autowired
	ToolRepository toolRepository;
	
	
	@Test
	@DatabaseSetup(value = { "classpath:dataset/initialisation.xml" })
	public void testfindTool() { 
		Optional<Tool> object = toolRepository.findById(1L);
		Tool tool = object.get();
		assertEquals("Screwdriver", tool.getName());
	}
	
	@Test
	@DatabaseSetup(value = { "classpath:dataset/initialisation.xml" })
	public void testGetAllTool() { 
		List<Tool> tool = toolRepository.findAll();
		assertEquals(4, tool.size());
	}
	
	@Test
	@DatabaseSetup(value = { "classpath:dataset/initialisation.xml" })
	public void testSaveTool() {
		Tool tool = new Tool();
		tool.setId(444L);
		tool.setName("Electric saw");
		
		toolRepository.save(tool);
		
		List<Tool> tools = toolRepository.findAll();
		assertEquals(5, tools.size());
	}
}
