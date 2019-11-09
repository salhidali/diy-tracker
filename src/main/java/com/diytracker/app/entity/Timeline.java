package com.diytracker.app.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Project timeline: every step of the project build including pictures, dates and details
 * @author salhidali
 *
 */

@Entity
@Table(name = "TIMELINE")
public class Timeline implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6785917834871316548L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "timeline")
    private Set<TimelineAction> actions = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<TimelineAction> getActions() {
		return actions;
	}

	public void setActions(Set<TimelineAction> actions) {
		this.actions = actions;
	}
    
    
}
