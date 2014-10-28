package com.csc.vim.framework.invoice.dao;

import java.util.Collection;

import com.csc.vim.framework.common.dao.IDctmDao;
import com.csc.vim.framework.invoice.model.Invoice;

public interface IInvoiceDao extends IDctmDao<Invoice>{

	public String linkPurchaseOrder(String pInvoiceId, String poType, String pPoId);
	
	public String linkSupplier(String pInvoiceId, String pSupplierId);
	
	public void linkBankingInformation(String pInvoiceId, Collection<String> pBankingInformation);
}
