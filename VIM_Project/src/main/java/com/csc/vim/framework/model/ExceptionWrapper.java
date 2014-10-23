package com.csc.vim.framework.model;

public class ExceptionWrapper {

	private String errorCode;
	private String messageCode;
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	
	public ExceptionWrapper() {
		super();
	}
	public ExceptionWrapper(String errorCode, String messageCode) {
		super();
		this.errorCode = errorCode;
		this.messageCode = messageCode;
	}
	
}
