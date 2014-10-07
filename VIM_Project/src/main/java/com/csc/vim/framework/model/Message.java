package com.csc.vim.framework.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {

	private String rObjectId;
	private String login;
	private String contentText;
	private String messageCode;
	
	public String getrObjectId() {
		return rObjectId;
	}
	
	public void setrObjectId(String rObjectId) {
		this.rObjectId = rObjectId;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getLogin() {
		return login;
	}

	
	public void setLogin(String login) {
		this.login = login;
	}

	public String getContentText() {
		return contentText;
	}
	
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}


	public Message(String rObjectId, String login, String contentText,
			String messageCode) {
		super();
		this.rObjectId = rObjectId;
		this.login = login;
		this.contentText = contentText;
		this.messageCode = messageCode;
	}

	public Message(){
		
	}
	
	
}
