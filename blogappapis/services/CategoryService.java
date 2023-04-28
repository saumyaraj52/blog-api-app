package com.blog.blogappapis.services;

import java.util.List;

import com.blog.blogappapis.payloads.CategoryDto;

public interface CategoryService{
	//POST
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//PUT
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	//GET
	CategoryDto getCategoryById(Integer categoryId);
	
	//GET
	List<CategoryDto> getAllCategories();	 
	
	//DELETE
	void deleteCategory(Integer categoryId);

}
