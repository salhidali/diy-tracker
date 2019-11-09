package com.diytracker.app.util;

import java.util.Date;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;

import com.diytracker.app.entity.Role;
import com.diytracker.app.entity.User;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@ComponentScan(basePackages ={"com.diytracker.app"})
public abstract class AbstractServiceTest {


	protected User getUser() {
		
		User user = new User();
		user.setFirstname("Firstname");
		user.setLastname("Lastname");
		user.setEmail("test@test.fr");
		user.setUsername("username");
		user.setPassword("password");
		user.setLastLoginDate(new Date());
		user.setEnabled(true);
		user.setLocked(false);
		
		Role role = new Role();
		role.setName("admin");
		
		user.getRoles().add(role);
		
		return user;
	}
	
	protected User getLockedUser() {
		
		User user = new User();
		user.setFirstname("FirstLock");
		user.setLastname("LastLock");
		user.setEmail("Locked@test.fr");
		user.setUsername("locked");
		user.setPassword("password");
		user.setEnabled(true);
		user.setLocked(true);
		
		Role role = new Role();
		role.setName("user");
		
		user.getRoles().add(role);
		
		return user;
	}
}
