package com.csc.vim.framework.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bankDetail")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankDetails {
	
	private String rObjectId;
	private String bankName;
	private String accountIban;
	private String accountName;

	
	public String getrObjectId() {
		return rObjectId;
	}

	public void setrObjectId(String rObjectId) {
		this.rObjectId = rObjectId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountIban() {
		return accountIban;
	}

	public void setAccountIban(String accountIban) {
		this.accountIban = accountIban;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	
	public BankDetails(String rObjectId, String bankName, String accountIban,
			String accountName) {
		super();
		this.rObjectId = rObjectId;
		this.bankName = bankName;
		this.accountIban = accountIban;
		this.accountName = accountName;
	}

	public BankDetails(){
		
	}
	
	
}
