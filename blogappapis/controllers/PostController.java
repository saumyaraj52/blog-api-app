package com.blog.blogappapis.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.blogappapis.configs.AppConstants;
import com.blog.blogappapis.payloads.ApiResponse;
import com.blog.blogappapis.payloads.PostDto;
import com.blog.blogappapis.payloads.PostResponse;
import com.blog.blogappapis.services.FileService;
import com.blog.blogappapis.services.PostService;

import  javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	//POST -Create Category
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createUser(@Valid @RequestBody PostDto postDto,@PathVariable("userId") Integer uid,@PathVariable("categoryId") Integer cid)
	{
		PostDto createdPostDto = this.postService.createPost(postDto,uid,cid);
		return new ResponseEntity<PostDto>(createdPostDto,HttpStatus.CREATED);
	}
	
	
	//GET by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") Integer uid)
	{
		List<PostDto> postDtos = this.postService.getPostsByUser(uid);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	//GET by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") Integer cid)
	{
		List<PostDto> postDtos = this.postService.getPostsByCategory(cid);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	//GET all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="PageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(value="PageSize",defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false)String sortDir)
	{
		PostResponse postResponse = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	//GET post by id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostsById(@PathVariable("postId") Integer pid)
	{
		PostDto postDto = this.postService.getPostById(pid);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	//PUT update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostsById(@Valid @RequestBody PostDto postDto,@PathVariable("postId") Integer pid)
	{
		PostDto postDtoUpdated = this.postService.updatePost(postDto, pid);
		return new ResponseEntity<PostDto>(postDtoUpdated,HttpStatus.OK);
	}
	
	//Delete delete post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer pid)
	{
		this.postService.deletePost(pid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully",true),HttpStatus.OK);
	}	
	
	
	//serach post by title
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> getPostsById(@PathVariable("keyword") String keyword)
	{
		List<PostDto> postDtos = this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	//Upload file
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> fileUpload(
			@RequestParam(value="image") MultipartFile image,
			@PathVariable("postId") Integer pid) throws IOException
	{
		PostDto postDto = this.postService.getPostById(pid);
		String fileName = this.fileService.uploadImage(path,image);
		postDto.setImageName(fileName);
		PostDto postDtoUpdated = this.postService.updatePost(postDto, pid);
		return new ResponseEntity<PostDto>(postDtoUpdated,HttpStatus.OK);
	}
	
	//method to serve file
	@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@RequestParam(value="image") MultipartFile image,
			@PathVariable("postId") String imageName,
			HttpServletResponse response ) throws IOException
	{
		InputStream resource = this.fileService.getResources(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
	//PUT -update user
	/*@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateUser(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("categoryId") Integer cid)
	{
		CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto,cid );
		return ResponseEntity.ok(updatedCategoryDto);
	}
	
	//Delete User
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("categoryId") Integer cid)
	{
		this.categoryService.deleteCategory(cid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);
	}
	
	//GET -get user
	@GetMapping("/{userId}")
	public ResponseEntity<CategoryDto> getSingleUser(@PathVariable("userId") Integer uid)
	{
		return ResponseEntity.ok(this.categoryService.getCategoryById(uid));
	}
	
	//GET -get users
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllUsers()
	{
		return ResponseEntity.ok(this.categoryService.getAllCategories());
	}*/
}
