package com.diytracker.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.diytracker.app.entity.Role;
import com.diytracker.app.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);
	
	public User findByUsername(String username);

    @Query("select u from User u where u.enabled = false or u.locked = true" )
    List<User> getDisabledUsers();
    
    @Query(value="select email from users where id in (select user_id from user_role  where role_id = (select id from role where name = :name))", nativeQuery=true)
    List<String> findByRole(@Param("name") String name);
    
}
