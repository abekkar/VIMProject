package com.csc.vim.framework.service;

import com.csc.vim.framework.model.Invoice;

public interface IBusinessService {

	public Invoice getInformationsFromDCTM(String r_object_id);
	
	public Invoice processAV2(Invoice pInvoice);
	
	public Invoice retrievingSapInformations(Invoice pInvoice);
	
	public Invoice createInvoiceIntoSAP(Invoice pInvoice);
	
	
	public void linkInvoiceDctmSap(Invoice pInvoice);
	
	public Invoice processInvoice(Invoice pInvoice);
	
	//Creation and booking of the invoices in status 6
	public void createInvoicesInSAP();
	
	//Create invoice with status 6 in SAP
		public Invoice createStatusSixInvoices(Invoice pInvoice);
		
	//Synchronise the status 7 invoices from SAP
	public void synchroniseStatusSevenInvoices();
	
	//Synchronise the status 8 invoices from SAP
		public void synchroniseStatusEightInvoices();
}
