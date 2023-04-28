package com.blog.blogappapis.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="posts")
@NoArgsConstructor
@Getter
@Setter
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name="post_title",nullable=false,length=100)
	private String title;
	
	@Column(length=10000)
	private String content;
	
	private String imageName;
	
	
	private Date addedDate;
	
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy="post",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	Set<Comment> comments = new HashSet<>();

	public Set<Comment> getComments() {
		// TODO Auto-generated method stub
		return this.comments;
	}

	public void setTitle(String title) {
		// TODO Auto-generated method stub
		this.title = title;	
	}

	public void setContent(String content) {
		// TODO Auto-generated method stub
		this.content=content;
	}

	public void setImageName(String imageName) {
		// TODO Auto-generated method stub
		this.imageName= imageName;
	}

	public void setAddedDate(Date date) {
		// TODO Auto-generated method stub
		this.addedDate = date;
	}

	public void setCategory(Category category) {
		// TODO Auto-generated method stub
		this.category=category;
	}

	public void setUser(User user) {
		// TODO Auto-generated method stub
		this.user=user;
	}

}
