package com.csc.vim.framework.util;

public class SapMessage {
	
	   MessageTypeEnum type;
	   public String messageCode;
	   public String messageText;
	public SapMessage(MessageTypeEnum type, String messageCode,
			String messageText) {
		super();
		this.type = type;
		this.messageCode = messageCode;
		this.messageText = messageText;
	}
	public MessageTypeEnum getType() {
		return type;
	}
	public void setType(MessageTypeEnum type) {
		this.type = type;
	}
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	
	public SapMessage(){
		
	}
	   
}
