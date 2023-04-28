package com.blog.blogappapis.entities;

//import java.util.List;

//import jakarta.persistence.Column;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="comments")
@NoArgsConstructor
@Getter
@Setter
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer commentId;
	
	//@Column(name="title",length = 100, nullable=false)
	private String content;
	
	
	@ManyToOne
	private Post post;


	public void setPost(Post post) {
		// TODO Auto-generated method stub
		this.post = post;
	}
	
	//@ManyToOne
	//private User user;
}
