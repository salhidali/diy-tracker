package com.diytracker.app.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class that defines different types of attachments without the content:
 * Entities can load these attachments eagerly without affecting performance
 * Content is loaded only when the user have to do it.
 * 
 * @author salhidali
 *
 */

@Entity
@Table(name = "ATTACHMENT")
public class Attachment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", nullable = true)
	private String name;
	
	@Column(name = "type", nullable = true)
	private String type;
	
	private long size;
	
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AttachmentContent content;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AttachmentContent thumbnail;

	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = true)
    private Project project;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	public AttachmentContent getContent() {
		return content;
	}

	public void setContent(AttachmentContent content) {
		this.content = content;
	}

	public AttachmentContent getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(AttachmentContent thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
