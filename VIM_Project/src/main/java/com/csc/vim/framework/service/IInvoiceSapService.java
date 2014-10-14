package com.csc.vim.framework.service;

import com.csc.vim.framework.model.Invoice;

public interface IInvoiceSapService {

	
	public String linkDocumentToSAP(Invoice pInvoice);
	
	public Invoice retrieveDataFromSAP(Invoice pInvoice);
	
	public Invoice createInvoiceWithPO (Invoice pInvoice);
	
	public Invoice createInvoiceWithoutPO (Invoice pInvoice);
	
	public void generatePDF();
}
