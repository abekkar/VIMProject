package com.csc.vim.framework.properties;

import java.util.HashMap;


public class BlockingCodeEnumList {


	public HashMap<BlockingCodeEnum,Boolean> BlockingCodeEnumList = new HashMap<BlockingCodeEnum,Boolean>();
	public boolean status = false ;
	
	public BlockingCodeEnumList() {
		super();
		BlockingCodeEnumList.put(BlockingCodeEnum.AuditControl,status);
		BlockingCodeEnumList.put(BlockingCodeEnum.FreeForPayment, status);
		BlockingCodeEnumList.put(BlockingCodeEnum.BlockedHeadOffice, status);
		BlockingCodeEnumList.put(BlockingCodeEnum.BlockedMills, status);
		BlockingCodeEnumList.put(BlockingCodeEnum.BlockedForBooking, status);
		BlockingCodeEnumList.put(BlockingCodeEnum.InvoiceVerification, status);
		BlockingCodeEnumList.put(BlockingCodeEnum.SkipAccount, status);
		BlockingCodeEnumList.put(BlockingCodeEnum.SuspicionInsolvency, status);
		BlockingCodeEnumList.put(BlockingCodeEnum.CheckIbanVATPO, status); 
	}

	
}
