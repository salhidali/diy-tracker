package com.diytracker.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.diytracker.app.entity.Role;
import com.diytracker.app.entity.User;
import com.diytracker.app.exception.ResourceNotFoundException;
import com.diytracker.app.repository.RoleRepository;
import com.diytracker.app.repository.UserRepository;
import com.diytracker.app.service.impl.UserServiceImpl;
import com.diytracker.app.util.AbstractServiceTest;


public class UserServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;
    
    @Mock
    RoleRepository roleRepository;



	@Test
	public void loadUserByUsernameTest() {
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(getUser());
		
		UserDetails user = userService.loadUserByUsername("test");
		
		assertNotNull(user);
		assertEquals("test", user.getUsername());
	}
	
	@Test
	public void loadLockedUserByUsernameTest() {
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(getLockedUser());
		
		UserDetails user = userService.loadUserByUsername("test");
		
		assertNotNull(user);
		assertEquals("test", user.getUsername());
	}
	
	@Test
	public void saveLastLoginDateTest() {
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(getUser());
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUser());
		
		User user = userService.saveLastLoginDate("test");
		
		assertEquals(new Date().getDay(), user.getLastLoginDate().getDay());
		
	}
	
	@Test
	public void loadUserByUsernameExceptionTest() {
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(null);
		UsernameNotFoundException exception = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			 userService.loadUserByUsername("ad");
		 });
		assertEquals("Invalid username or password", exception.getMessage());
		
	}

	@Test
	public void deleteUserTest() throws ResourceNotFoundException {
		User user = getUser();
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);

		userService.deleteUser("ad");

	    verify(userRepository, times(1)).delete(user);
	}
	
	@Test
	public void deleteUserExceptionTest() {
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(null);
		UsernameNotFoundException exception = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			 userService.deleteUser("ad");
		 });
		assertEquals("Invalid username or password", exception.getMessage());
	}
	
	@Test
	public void getUserTest() throws UsernameNotFoundException {
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(getUser());
		
		User user = userService.getUser("test");
		
		assertNotNull(user);
		assertEquals("username", user.getUsername());
	}
	
	@Test
	public void getUserExceptionTest() throws UsernameNotFoundException {
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(null);
		UsernameNotFoundException exception = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			 userService.getUser("ad");
		 });
		assertEquals("Invalid username or password", exception.getMessage());
	}

	@Test
	public void saveUserTest() {
		User user = new User();
		user.setUsername("test");
		user.setFirstname("firstname");
		user.setLastname("lastname");
		user.setEmail("user@user.de");
		user.setPassword("pass");
		
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
		
		User dbUser = userService.saveUser(user);
		
		assertEquals("test", dbUser.getUsername());
	}
	
	@Test
	public void getRoleTest() {
		Role user = new Role();
		user.setName("user");
		Optional<Role> optional = Optional.of(user);
		
		Mockito.when(roleRepository.findById(Mockito.anyLong())).thenReturn(optional);
		
		Role role = userService.getRole(1L);
		assertEquals("user", role.getName());
	}
	
	@Test
	public void getDisabledUsersTest() {
		List<User> users = new ArrayList<User>();
		users.add(getLockedUser());
		
		Mockito.when(userRepository.getDisabledUsers()).thenReturn(users);
		
		List<User> dbUsers = userService.getDisabledUsers();
		
		assertEquals(1, dbUsers.size());
		assertEquals(true, dbUsers.get(0).getLocked());
	}
	
	@Test
	public void getAdminsMailTest() {
		
		Role user = new Role();
		user.setName("admin");
		Optional<Role> optional = Optional.of(user);
		
		Mockito.when(roleRepository.findById(Mockito.anyLong())).thenReturn(optional);
		
		List<String> emails = new ArrayList<>();
		emails.add("test@test.fr");
		
		Mockito.when(userRepository.findByRole(anyString())).thenReturn(emails);
		
		String[] admins = userService.getAdminsMail();
		
		assertEquals(1, admins.length);
		assertEquals("test@test.fr", admins[0]);
	}
	
	@Test
	public void isAdminOKTest() throws ResourceNotFoundException {
		
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(getUser());
		
		assertTrue(userService.isAdmin("admin"));
	}
	
	@Test
	public void isAdminKOTest() throws ResourceNotFoundException {
		Mockito.when(userRepository.findByUsername(anyString())).thenReturn(getLockedUser());
		
		assertFalse(userService.isAdmin("user"));
	}


}
