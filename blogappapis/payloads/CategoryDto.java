package com.blog.blogappapis.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	private Integer categoryId;
	
	@NotBlank
	@Size(min=4,message="Minimum size of category title is 4")
	private String categoryTitle;
	
	@NotBlank
	@Size(min=10,message="Minimum size of category description is 10")
	private String categoryDescription;

	public String getCategoryTitle() {
		// TODO Auto-generated method stub
		return this.categoryTitle;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public String getCategoryDescription() {
		// TODO Auto-generated method stub
		return this.categoryDescription;
	}

}
