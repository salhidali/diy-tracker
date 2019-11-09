package com.diytracker.app.repository;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.diytracker.app.entity.Budget;
import com.diytracker.app.util.AbstractRepositoryTest;
import com.github.springtestdbunit.annotation.DatabaseSetup;


public class BudgetRepositoryTest extends AbstractRepositoryTest {

	@Autowired
	BudgetRepository budgetRepository;
	
	
	@Test
	@DatabaseSetup(value = { "classpath:dataset/initialisation.xml" })
	public void testfindBudget() { 
		Optional<Budget> object = budgetRepository.findById(1L);
		Budget budget = object.get();
		assertEquals(new BigDecimal("20.00"), budget.getCurrent());
	}
	
	@Test
	@DatabaseSetup(value = { "classpath:dataset/initialisation.xml" })
	public void testGetAllBudgets() { 
		List<Budget> budgets = budgetRepository.findAll();
		assertEquals(2, budgets.size());
	}
	
	@Test
	@DatabaseSetup(value = { "classpath:dataset/initialisation.xml" })
	public void testSaveBudget() {
		Budget budget = new Budget();
		budget.setId(444L);
		budget.setCurrent(new BigDecimal(12.3));
		budget.setDate(new Date(2019, 05,05));
		budget.setMax(new BigDecimal(22.5));
		
		budgetRepository.save(budget);
		
		List<Budget> budgets = budgetRepository.findAll();
		assertEquals(3, budgets.size());
	}
}
