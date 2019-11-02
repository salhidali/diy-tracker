package com.diytracker.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diytracker.app.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
