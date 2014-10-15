package com.csc.vim.framework.properties;

public enum InvoiceCategoryEnum {

	WhitPoGR(1,"WhitPoGR"),
	WhitPO_GR(2,"WhitPO_GR"),
	WhitoutPO(3,"WhitoutPO");
	
	private int categoryId;
	private String description;
	
	InvoiceCategoryEnum(int categoryId,String description){
		this.setCategoryId(categoryId);
		this.setDescription(description);
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	  
	  
}
