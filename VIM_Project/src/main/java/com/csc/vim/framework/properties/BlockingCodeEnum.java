package com.csc.vim.framework.properties;

public enum BlockingCodeEnum {
	
	/*Add the blocking codes 
	 * B - M - N - T - V - W
	*/
	FreeForPayment(0, ""),
	AuditControl(1, "R"),
	Bankruptcy(2, "D"),
	BlockedHeadOffice(3, "S"),
	BlockedMills(4, "U"),
	BlockedForPayment(5, "A"),
	BlockedForBooking(6, "S"),
	InvoiceVerification(7, "R"),
	SkipAccount(8, "*"),
	SuspicionInsolvency(9, "E"),
	CheckIbanVATPO(10, "I"),
	BlockedForValidation(11,"V"),
	BlockedForAccounting(12,"T"),
	MultipleBlockingCode(13,"N");
	
	//Members
	private final int codeId;
	private final String codeSap;

	public int getCodeId() {
		return codeId;
	}



	public String getDescription() {
		return codeSap;
	}
	
	BlockingCodeEnum(int codeId,String description){
		this.codeId = codeId;
		this.codeSap=description;
	}

	
}
