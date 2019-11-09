package com.diytracker.app.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diytracker.app.entity.Role;
import com.diytracker.app.entity.User;
import com.diytracker.app.exception.ResourceNotFoundException;
import com.diytracker.app.repository.RoleRepository;
import com.diytracker.app.repository.UserRepository;
import com.diytracker.app.service.UserService;


/**
 * @author salhidali
 *
 */

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	/**
	 * Method to return UserDetails after successful login
	 * 
	 * @param username
	 * @return UserDetails object
	 */
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			LOGGER.error("User not found!");
			throw new UsernameNotFoundException("Invalid username or password");
		}

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), user.getEnabled(),
				true, true, !user.getLocked(), getAuthorities(user));

	}

	@Transactional
	private List<GrantedAuthority> getAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}

		return authorities;
	}

	/**
	 * Method to update last login date once the user signed in
	 *
	 * @param username
	 * @return User object
	 * 
	 */
	@Transactional(readOnly = false)
	@Override
	public User saveLastLoginDate(String username) {
		User user = userRepository.findByUsername(username);
		user.setLastLoginDate(new Date());
		return userRepository.save(user);

	}

	/**
	 * Method to return the user entity (not the Spring user but the app user)
	 * 
	 * @param username
	 * @return User object
	 */
	@Override
	@Transactional
	public User getUser(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			LOGGER.error("User not found!");
			throw new UsernameNotFoundException("Invalid username or password");
		}

		return userRepository.findByUsername(username);
	}

	/**
	 * Method to save a new user or updated user
	 * 
	 * @param the user to save/update
	 * @return User object
	 */
	@Transactional(readOnly = false)
	@Override
	public User saveUser(User user) {
		return userRepository.save(user);

	}

	/**
	 * Method to return a role from an id
	 * 
	 * @param the role id
	 * @return Role object
	 */
	@Override
	@Transactional
	public Role getRole(Long idRole) {
		return roleRepository.findById(idRole).get();
	}
	
	/**
	 * Method to return a list of either disabled or locked user:
	 * will be used by admin to manage these users
	 * 
	 * @param None
	 * @return List<User> object
	 */
	
	@Transactional
	public List<User> getDisabledUsers() {
		return userRepository.getDisabledUsers();
	}

	/**
	 * This method returns all admin mail adresses as an array in order to user then for
	 * mail sending
	 * 
	 * @param None
	 * @return String array of mail adresses
	 */
	@Transactional
	public String[] getAdminsMail() {
		List<String> admins =  userRepository.findByRole(getRole(1L).getName());
		
		String[] adminStr = admins.toArray(new String[admins.size()]);
//		for(int i = 0; i < admins.size(); i++) {
//			adminStr[i] = ((User)admins.toArray()[i]).getEmail();
//		}
		
		return adminStr;
	}
	
	/**
	 * This method checks wether a user is an admin or regular user
	 * 
	 * @param username
	 * @return boolean (true or false): user is admin or not
	 */
	@Transactional
	public boolean isAdmin(String username) throws ResourceNotFoundException {
		User user = getUser(username);
		
		for(Role role: user.getRoles()) {
			if(role.getName().equals("admin")) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void deleteUser(String username) throws ResourceNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			LOGGER.error("User not found!");
			throw new ResourceNotFoundException("Invalid username or password");
		}

		userRepository.delete(user);
	}
}
