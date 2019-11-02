package com.diytracker.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DIY_USER")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7893539265404911723L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "username", unique = true, nullable = false, length = 100, columnDefinition = "VARCHAR(100)")
	private String username;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Column(name = "firstname", nullable = false, length = 100)
	private String firstname;
	
	@Column(name = "lastname", nullable = false, length = 100)
	private String lastname;
	
	@Column(name = "email", nullable = false, length = 100)
	private String email;
	
	@Column(name = "failed_logins")
	private Integer failedLogins;

	@Column(name = "enabled")
	private Boolean enabled;

	@Column(name = "locked")
	private Boolean locked;

	@Column(name = "last_login_date", length = 23)
	private Date lastLoginDate;

	@ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinTable(name="user_role", 
		joinColumns=@JoinColumn(name="user_id", unique=false),
		inverseJoinColumns=@JoinColumn(name="role_id", unique=false))
	private Set<Role> roles = new HashSet<Role>();
	
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Project> projects;

    
	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, Integer failedLogins,
			Boolean enabled, Boolean locked, Date lastLoginDate,
			Set<Role> roles) {
		this.username = username;
		this.password = password;
		this.failedLogins = failedLogins;
		this.enabled = enabled;
		this.locked = locked;
		this.lastLoginDate = lastLoginDate;
		this.roles = roles;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getFailedLogins() {
		return this.failedLogins;
	}

	public void setFailedLogins(Integer failedLogins) {
		this.failedLogins = failedLogins;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getLocked() {
		return this.locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
