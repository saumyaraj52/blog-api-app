package com.blog.blogappapis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blogappapis.payloads.ApiResponse;
import com.blog.blogappapis.payloads.CommentDto;
import com.blog.blogappapis.services.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {
	

	@Autowired
	private CommentService commentService;
	
	//POST -Create comment
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createUser(@PathVariable("postId") Integer postId,@Valid @RequestBody CommentDto commentDto)
	{
		CommentDto createdCommentDto = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createdCommentDto,HttpStatus.CREATED);
	}
	
	//Delete comment
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("commentId") Integer cid)
	{
		this.commentService.deleteComment(cid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully",true),HttpStatus.OK);
	}

}
