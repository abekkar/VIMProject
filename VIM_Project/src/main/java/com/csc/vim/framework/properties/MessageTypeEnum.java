package com.csc.vim.framework.properties;

public enum MessageTypeEnum {

	Succes("S"),
	Error("E"),
	Avert("W"),
	Info("I"),
	Abandon("A");
	
	private final String type;

	MessageTypeEnum(String type){
		this.type = type;
		
	}

	public String getType() {
		return type;
	}
}
