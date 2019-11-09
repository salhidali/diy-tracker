package com.diytracker.app.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.diytracker.app.entity.Role;
import com.diytracker.app.entity.User;
import com.diytracker.app.exception.ResourceNotFoundException;

/**
 * @author salhidali
 *
 */
public interface UserService {

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	public User saveLastLoginDate(String username);

	public User getUser(String username) throws UsernameNotFoundException;

	public User saveUser(User user);
	
	public Role getRole(Long id);
	
	public List<User> getDisabledUsers();
	
	public String[] getAdminsMail();
	
	public boolean isAdmin(String username) throws ResourceNotFoundException;
	
	public void deleteUser(String username) throws ResourceNotFoundException;
}
