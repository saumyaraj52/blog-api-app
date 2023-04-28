package com.blog.blogappapis.services;

import java.util.List;

import com.blog.blogappapis.payloads.PostDto;
import com.blog.blogappapis.payloads.PostResponse;

public interface PostService {
	
	 PostDto createPost(PostDto post,Integer userId,Integer categoryId);
	 PostDto updatePost(PostDto post,Integer postId);
	 PostDto getPostById(Integer postId);
	 PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	 
	 List<PostDto> getPostsByUser(Integer userId);
	 List<PostDto> getPostsByCategory(Integer categoryId);
	 List<PostDto> searchPosts(String keyword);
	void deletePost(Integer postId);
	 

}
