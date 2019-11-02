package com.diytracker.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diytracker.app.entity.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

}
