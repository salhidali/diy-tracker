package com.diytracker.app.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class that contains Attachment content.
 * 
 * @author salhidali
 *
 */

@Entity
@Table(name = "PROJECT")
public class Project implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 182253648873709140L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Project title
	private String title;
	
	//Project Description: A long text that contains all the work done ==> convert to html format
	private String description;
	
	//Project creation date only : updates will be handled in the timeline.
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
    @OneToOne(cascade = CascadeType.ALL)
    private Budget budget;
	
    @OneToMany(cascade = CascadeType.ALL)
    @org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
    @JoinColumn(name="project_id", insertable = false, updatable = false)
    @OrderBy("dateTime ASC")
    private Set<Comment> comments;
    
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Attachment> attachments = new HashSet<>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Supplies> supplies = new HashSet<>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Tool> tools = new HashSet<>();
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Timeline timeline;

    @ManyToOne
    @JoinColumn
    private Category category;
    
    @ManyToOne
    @JoinColumn
    private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public Set<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}

	public Set<Supplies> getSupplies() {
		return supplies;
	}

	public void setSupplies(Set<Supplies> supplies) {
		this.supplies = supplies;
	}

	public Set<Tool> getTools() {
		return tools;
	}

	public void setTools(Set<Tool> tools) {
		this.tools = tools;
	}

	public Timeline getTimeline() {
		return timeline;
	}

	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
