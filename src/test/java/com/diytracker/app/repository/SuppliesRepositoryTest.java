package com.diytracker.app.repository;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.diytracker.app.entity.Supplies;
import com.diytracker.app.util.AbstractRepositoryTest;
import com.github.springtestdbunit.annotation.DatabaseSetup;


public class SuppliesRepositoryTest extends AbstractRepositoryTest {

	@Autowired
	SuppliesRepository suppliesRepository;
	
	
	@Test
	@DatabaseSetup(value = { "classpath:dataset/initialisation.xml" })
	public void testfindSupplies() { 
		Optional<Supplies> object = suppliesRepository.findById(1L);
		Supplies supply = object.get();
		assertEquals("Electrical tape", supply.getName());
	}
	
	@Test
	@DatabaseSetup(value = { "classpath:dataset/initialisation.xml" })
	public void testGetAllSupplies() { 
		List<Supplies> supplies = suppliesRepository.findAll();
		assertEquals(4, supplies.size());
	}
	
	@Test
	@DatabaseSetup(value = { "classpath:dataset/initialisation.xml" })
	public void testSaveSupplies() {
		Supplies supply = new Supplies();
		supply.setId(444L);
		supply.setName("WD 40");
		supply.setPrice(new BigDecimal(10));
		supply.setPurchaseDate(new Date(2019, 11, 11));;
		
		suppliesRepository.save(supply);
		
		List<Supplies> supplies = suppliesRepository.findAll();
		assertEquals(5, supplies.size());
	}
}
