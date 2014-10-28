package com.csc.vim.framework.bankinformation.model;

import com.csc.vim.framework.common.model.DematObject;

@lombok.Getter
@lombok.Setter
public class BankingInformation extends DematObject{
	private String accountName;
	private String bankName;
	private String iban;
}
