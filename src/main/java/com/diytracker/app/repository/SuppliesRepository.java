package com.diytracker.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diytracker.app.entity.Supplies;

public interface SuppliesRepository extends JpaRepository<Supplies, Long> {

}
