package com.csc.vim.framework.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.properties.InvoiceCategoryEnum;
import com.csc.vim.framework.properties.InvoiceFamilyEnum;
import com.csc.vim.framework.properties.Parameters;

@Component
public class BusinessService {

	@Autowired
	Parameters parametersProperties;
	@Autowired
	InvoiceDCTMService invoiceDCTMServiceInstance;
	
	@Autowired
	InvoiceSapService invoiceSapServiceInstance;
	
	public Invoice getInformationsFromDCTM(String r_object_id){
		// Get Invoice Data from Documentum
		Invoice pInvoice = new Invoice();
		pInvoice.setrObjectId(r_object_id);
		return  invoiceDCTMServiceInstance.readInvoiceFromDctm(pInvoice);
	}
	
	public Invoice processAV2(Invoice pInvoice){
		
		// Get Invoice Data from Documentum
		pInvoice  = invoiceDCTMServiceInstance.readInvoiceFromDctm(pInvoice);
		
		//Get Invoice Data from SAP
		pInvoice = invoiceSapServiceInstance.retrieveDataFromSAP(pInvoice);

		// Check Blocking code
		if (pInvoice.getInvoiceFamily() == InvoiceFamilyEnum.TradingCoating.getCategoryId() || pInvoice.getInvoiceFamily() == InvoiceFamilyEnum.ManualFiCoInput.getCategoryId())
		{
			if (pInvoice.getBlockingCodeT() == "F"){
				pInvoice.setBlockingCodeT("T");
			}
		}
		
		pInvoice = createInvoiceIntoSAP(pInvoice);
		//TODO
		//Link SAP Document with Documentum object
		//invoiceSapServiceInstance.linkDocumentToSAP(pInvoice);
		
		return pInvoice;
		 	
	}
	
	public Invoice retrievingSapInformations(Invoice pInvoice){
		if (pInvoice.getrObjectId()!= null)
		{
			return invoiceDCTMServiceInstance.updateDctmInvoice(invoiceSapServiceInstance.retrieveDataFromSAP(pInvoice));
		}
		return null;
	}
	
	public Invoice createInvoiceIntoSAP(Invoice pInvoice){
		//The invoice is booked during the creation 
		if (pInvoice.getrObjectId()!= null)
		{
			invoiceDCTMServiceInstance.readInvoiceFromDctm(pInvoice);
			if ((pInvoice.getInvoiceFamily() == InvoiceCategoryEnum.WhitPoGR.getCategoryId()) || (pInvoice.getInvoiceFamily() == InvoiceCategoryEnum.WhitPO_GR.getCategoryId()) )
					pInvoice = invoiceSapServiceInstance.createInvoiceWithPO(pInvoice);
			else
			{
				pInvoice = invoiceSapServiceInstance.createInvoiceWithoutPO(pInvoice);
			}
			return invoiceDCTMServiceInstance.updateDctmInvoice(pInvoice);
		}
		return null;
	}
	
	
	public void linkInvoiceDctmSap(Invoice pInvoice){
		if (pInvoice.getrObjectId()!= null){
			invoiceSapServiceInstance.linkDocumentToSAP(pInvoice);
		}
	}
	
	public Invoice processInvoice(Invoice pInvoice){

			if (pInvoice.getInvoiceStatus() == Integer.parseInt(parametersProperties.getINV_DCTM_STATUS_ZERO()))
				pInvoice = invoiceSapServiceInstance.retrieveDataFromSAP(pInvoice);
			// Verifying the invoice family
			if (pInvoice.getInvoiceFamily() == InvoiceFamilyEnum.ManualFiCoInput.getCategoryId() || pInvoice.getInvoiceFamily() == InvoiceFamilyEnum.TradingCoating.getCategoryId())
			{
				if (pInvoice.getBlockingCodeT() == "F"){
					pInvoice.setBlockingCodeT("T");
				}
			}
			return createInvoiceIntoSAP(pInvoice);
	}
	
	//Creation and booking of the invoices in status 6
	public void createInvoicesInSAP(){
		List<Invoice> listOfInvoices = invoiceDCTMServiceInstance.retrieveInvoicesByStatus(Integer.parseInt(parametersProperties.getINV_DCTM_STATUS_SIX()));
		for (Invoice invoiceInstance : listOfInvoices) {
			processInvoice(invoiceInstance);	
		}
	}
	
	//Create invoice with status 6 in SAP
	public Invoice createStatusSixInvoices(Invoice pInvoice){
		// Get Invoice Data from Documentum
		pInvoice  = invoiceDCTMServiceInstance.readInvoiceFromDctm(pInvoice);
		
		// Check Blocking code
		if (pInvoice.getInvoiceFamily() == InvoiceFamilyEnum.TradingCoating.getCategoryId() || pInvoice.getInvoiceFamily() == InvoiceFamilyEnum.ManualFiCoInput.getCategoryId())
		{
			if (pInvoice.getBlockingCodeT() == "F"){
				pInvoice.setBlockingCodeT("T");
			}
		}
		
		//Creating MM ou FI SAP Invoice
		if(pInvoice.getPurchaseOrder()!= null)
		{
			if (pInvoice.getPurchaseOrder().getPoNumber()!=null)
				pInvoice = invoiceSapServiceInstance.createInvoiceWithPO(pInvoice);
			else
				pInvoice = invoiceSapServiceInstance.createInvoiceWithoutPO(pInvoice);
		}
		
		//Updating DCTM Invoice
		pInvoice = invoiceDCTMServiceInstance.updateDctmInvoice(pInvoice);
		//TODO
		//Link SAP Document with Documentum object
		//invoiceSapServiceInstance.linkDocumentToSAP(pInvoice);
		
		return pInvoice;
	}
		
	//Synchronise the status 7 invoices from SAP
	public void synchroniseStatusSevenInvoices(){
		List<Invoice> listOfInvoices = invoiceDCTMServiceInstance.retrieveInvoicesByStatus(Integer.parseInt(parametersProperties.getINV_DCTM_STATUS_SEVEN()));
		for (Invoice invoiceInstance : listOfInvoices) {
			invoiceInstance = invoiceSapServiceInstance.retrieveDataFromSAP(invoiceInstance);
			invoiceDCTMServiceInstance.updateDctmInvoice(invoiceInstance);
		}
	}
	
	//Synchronise the status 8 invoices from SAP
	public void synchroniseStatusEightInvoices(){
		List<Invoice> listOfInvoices = invoiceDCTMServiceInstance.retrieveInvoicesByStatus(Integer.parseInt(parametersProperties.getINV_DCTM_STATUS_EIGHT()));
		for (Invoice invoiceInstance : listOfInvoices) {
			invoiceInstance = invoiceSapServiceInstance.retrieveDataFromSAP(invoiceInstance);
			invoiceDCTMServiceInstance.updateDctmInvoice(invoiceInstance);
		}
	}
}
