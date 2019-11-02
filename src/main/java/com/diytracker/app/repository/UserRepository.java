package com.diytracker.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.diytracker.app.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);
	
	public User findByUsername(String username);

    @Query("select u from User u where u.enabled = false or u.locked = true" )
    List<User> getDisabledUsers();
    
}
