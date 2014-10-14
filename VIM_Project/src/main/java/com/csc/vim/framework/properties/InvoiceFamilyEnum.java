package com.csc.vim.framework.properties;

public enum InvoiceFamilyEnum {

	Standard(1,"Standard"),
	TradingCoating(2,"TradingCoating"),
	Freight(3,"Freight"),
	ManualFiCoInput(4,"ManualFiCoInput");
	
	
	private int familyId;
	private String description;
	
	InvoiceFamilyEnum(int categoryId,String description){
		this.setCategoryId(categoryId);
		this.setDescription(description);
	}

	public int getCategoryId() {
		return familyId;
	}

	public void setCategoryId(int categoryId) {
		this.familyId = categoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
