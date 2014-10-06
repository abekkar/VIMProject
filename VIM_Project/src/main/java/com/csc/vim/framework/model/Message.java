package com.csc.vim.framework.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {

	private String rObjectId;
	private String login;
	private int sequence;
	private String contentText;
	private Date date;
	private String role;
	
	public String getrObjectId() {
		return rObjectId;
	}
	@XmlElement
	public void setrObjectId(String rObjectId) {
		this.rObjectId = rObjectId;
	}
	public String getRole() {
		return role;
	}
	@XmlElement
	public void setRole(String role) {
		this.role = role;
	}
	public String getLogin() {
		return login;
	}
	public Date getDate() {
		return date;
	}
	@XmlElement
	public void setDate(Date date) {
		this.date = date;
	}
	@XmlElement
	public void setLogin(String login) {
		this.login = login;
	}
	public int getSequence() {
		return sequence;
	}
	@XmlElement
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getContentText() {
		return contentText;
	}
	@XmlElement
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
	public Message(String rObjectId, String login, int sequence,
			String contentText, Date date, String role) {
		super();
		this.rObjectId = rObjectId;
		this.login = login;
		this.sequence = sequence;
		this.contentText = contentText;
		this.date = date;
		this.role = role;
	}
	
	public Message(){
		
	}
	
	
}
