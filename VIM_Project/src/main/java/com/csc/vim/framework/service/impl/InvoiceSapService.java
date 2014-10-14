package com.csc.vim.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.csc.vim.framework.dao.impl.InvoiceSapDao;
import com.csc.vim.framework.model.Invoice;

@Component
public class InvoiceSapService {

	@Autowired 
	InvoiceSapDao invoiceSapDaoInstance;
	
	public String linkDocumentToSAP(Invoice pInvoice){
		return invoiceSapDaoInstance.linkDocument(pInvoice);
	}
	
	public Invoice retrieveDataFromSAP(Invoice pInvoice){
		return  invoiceSapDaoInstance.retrieveInvoice(pInvoice);
	}
	
	public Invoice createInvoiceWithPO (Invoice pInvoice){
		return invoiceSapDaoInstance.createWithPurchaseOrder(pInvoice);
	}
	
	public Invoice createInvoiceWithoutPO (Invoice pInvoice){
		return invoiceSapDaoInstance.createWithoutPurchaseOrder(pInvoice);
	}
	
	public void generatePDF(){
		
	}

}
