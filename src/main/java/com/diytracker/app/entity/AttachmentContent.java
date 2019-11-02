package com.diytracker.app.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class that contains Attachment content.
 * 
 * @author salhidali
 *
 */

@Entity
@Table(name = "ATTACHMENT_CONTENT")
public class AttachmentContent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@Lob @Basic(fetch = FetchType.LAZY)
    @Column(name="content", nullable=true)
    private byte[] content;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}
