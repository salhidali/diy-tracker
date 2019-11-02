package com.diytracker.app.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.diytracker.app.util.LocalDateTimePersistenceConverter;
import com.diytracker.app.util.MarkdownConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "COMMENT")
public class Comment implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -241648888806676591L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(nullable = false)
    @NotBlank
    private String commentText;

    @Column(nullable = false)
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime dateTime;

    @Column(nullable = true)
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime modifiedDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="use_case_id", nullable = true)
    private Long ucId;

	@Column(nullable = false)
    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Comment parentComment;

    @OneToMany(mappedBy="parentComment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("dateTime ASC")
    @JsonManagedReference
    private List<Comment> childrenComments = new ArrayList<>();

    public int commentLevel() {
        Comment comment = this;
        int level = 0;
        while ((comment = comment.getParentComment()) != null)
            level++;
        return level;
    }

    public boolean userCanDelete() {
        return LocalDateTime.now().isBefore(maxDeleteTime());
    }

    public LocalDateTime maxDeleteTime() {
        return this.dateTime.plusMinutes(10);
    }

    // should refactor to store dates in UTC in database

    public long maxDeleteTimeUnixTimestamp() {
        return maxDeleteTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public boolean userCanEdit() {
        return LocalDateTime.now().isBefore(maxEditTime());
    }

    public LocalDateTime maxEditTime() {
        return this.dateTime.plusMinutes(180);
    }

    public long maxEditTimeUnixTimestamp() {
        return maxEditTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }



    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentText() {
        return this.commentText;
    }

    public String getCommentTextHtml() {
        return MarkdownConverter.toHtml(getCommentText());
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getModifiedDateTime() {
        return this.modifiedDateTime;
    }

    public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUcId() {
		return ucId;
	}

	public void setUcId(Long ucId) {
		this.ucId = ucId;
	}

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Comment getParentComment() {
        return this.parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public List<Comment> getChildrenComments() {
        return this.childrenComments;
    }

    public void setChildrenComments(List<Comment> childrenComments) {
        this.childrenComments = childrenComments;
    }

}
