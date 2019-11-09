package com.diytracker.app.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Project type: 
 * - A new build
 * - A repair
 * 
 * @author salhidali
 *
 */
@Entity
@Table(name = "TYPE")
public class Type implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4463362004392085597L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
