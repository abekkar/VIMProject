package com.csc.vim.framework.service;

import java.util.List;

import com.csc.vim.framework.model.Invoice;

public interface IInvoiceDCTMService {

	public Invoice create(Invoice pInvoice);
	
	public Invoice readInvoiceFromDctm(Invoice pInvoice);
	
	public Invoice updateDctmInvoice(Invoice pInvoice);
	
	public List<Invoice> retrieveInvoicesByStatus(Integer status);
}
