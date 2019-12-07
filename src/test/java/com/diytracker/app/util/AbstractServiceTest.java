package com.diytracker.app.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;

import com.diytracker.app.entity.Project;
import com.diytracker.app.entity.Role;
import com.diytracker.app.entity.User;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@ComponentScan(basePackages ={"com.diytracker.app"})
public abstract class AbstractServiceTest {


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    
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
	
	protected List<Project> getProjects() {
		Project proj1 = new Project();
		proj1.setTitle("Project 1");
		proj1.setDescription("Description of project 1");
		
		Project proj2 = new Project();
		proj2.setTitle("Project 2");
		proj2.setDescription("Description of project 2");
		
		List<Project> projects = new ArrayList<Project>();
		projects.add(proj1);
		projects.add(proj2);
		
		return projects;
	}
}
