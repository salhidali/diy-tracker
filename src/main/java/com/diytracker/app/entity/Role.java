package com.diytracker.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "ROLE")
@ApiModel(description = "Role entity : user roles (ADMIN_ROLE/USER_ROLE)")
public class Role implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6778040320020780058L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated role ID")
	private int id;

	@Column(name = "name", nullable = false, length = 50)
	@ApiModelProperty(notes = "The role name (ADMIN_ROLE/USER_ROLE)")
	private String name;

	public Role() {
	}

	public Role(int id, String name) {
		this.id = id;
		this.name = name;
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
