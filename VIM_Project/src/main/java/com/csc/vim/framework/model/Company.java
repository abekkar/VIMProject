package com.csc.vim.framework.model;

import com.csc.vim.framework.common.model.DematObject;

@lombok.Getter
@lombok.Setter
public class Company extends DematObject {
	// Business Field
	protected String number;
	protected String industry;
	protected String defaultIban;
	protected String address;
	protected String postCode;
	protected String city;
	protected String country;
	protected String email;
	protected Boolean cpd;
	protected String taxNumber;
	protected String name;
	protected String vatNumber;
}
