package com.csc.vim.framework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.properties.Parameters;
import com.csc.vim.framework.util.InvoiceFamilyEnum;

@Component
public class BusinessService {

	@Autowired
	Parameters parametersProperties;
	@Autowired
	InvoiceDCTMService invoiceDCTMServiceInstance;
	
	@Autowired
	InvoiceSapService invoiceSapServiceInstance;
	
	public Invoice processAV2(Invoice pInvoice){
		// Get Invoice Data from Documentum
		pInvoice  = invoiceDCTMServiceInstance.readInvoiceFromDctm(pInvoice);
		//Get Invoice Data from SAP
		pInvoice = invoiceSapServiceInstance.retrieveDataFromSAP(pInvoice);
		// Check Blocking code
//		if (pInvoice.getBlockingCode().isEmpty()){
//			pInvoice.setBlockingCode(new ArrayList<String>());
//			if ( !((pInvoice.getPurchaseOrder().getPoNumber()!=null ) && ( !pInvoice.getGoodReceiptNumber().isEmpty()) && (Double.parseDouble(pInvoice.getInvoiceGrossAmount()) < Double.parseDouble(parametersProperties.getINV_DCTM_LCT()))) )
//				pInvoice.getBlockingCode().add(BlockingCodeEnum.BlockedForValidation.getDescription());
//		}
//		if (pInvoice.getInvoiceFamily() == InvoiceFamilyEnum.TradingCoating.getCategoryId() || pInvoice.getInvoiceFamily() == InvoiceFamilyEnum.ManualFiCoInput.getCategoryId()){
//			if (pInvoice.getBlockingCode().isEmpty()){
//				pInvoice.setBlockingCode(new ArrayList<String>());
//			}
//			pInvoice.getBlockingCode().add(BlockingCodeEnum.BlockedForAccounting.getDescription());
//		}
//		if(pInvoice.getBlockingCode().size()>=2 && pInvoice.getBlockingCode().contains(BlockingCodeEnum.BlockedForAccounting.getDescription())){
//			pInvoice.getBlockingCode().add(BlockingCodeEnum.MultipleBlockingCode.getDescription());
//		}
		//Updating DCTM Invoice
		pInvoice = invoiceDCTMServiceInstance.updateDctmInvoice(pInvoice);
		
		//Creating MM SAP Invoice 
		pInvoice = invoiceSapServiceInstance.createInvoiceWithoutPO(pInvoice);
		
		//Link SAP Document with Documentum object
		invoiceSapServiceInstance.linkDocumentToSAP(pInvoice);
		
		//Update Documentum Invoice Data
		return invoiceDCTMServiceInstance.updateDctmInvoice(pInvoice);
		 	
	}
	
	public void retrievingSapInformations(Invoice pInvoice){
		if (pInvoice.getrObjectId()!= null){
			pInvoice = invoiceSapServiceInstance.retrieveDataFromSAP(pInvoice);
			invoiceDCTMServiceInstance.updateDctmInvoice(pInvoice);
		}		
	}
	
	public void createInvoiceIntoSAP(Invoice pInvoice){
		//The invoice is booked during the creation 
		if (pInvoice.getrObjectId()!= null){
			invoiceDCTMServiceInstance.readInvoiceFromDctm(pInvoice);
			
			if ( null != pInvoice.getPurchaseOrder().getPoNumber() )
				pInvoice = invoiceSapServiceInstance.createInvoiceWithPO(pInvoice);
			else
				pInvoice = invoiceSapServiceInstance.createInvoiceWithoutPO(pInvoice);
			
			invoiceDCTMServiceInstance.updateDctmInvoice(pInvoice);
		}
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
				//TODO
			if (null != pInvoice.getPurchaseOrder().getPoNumber()){
				//Creating SAP MM Invoice 
				pInvoice = invoiceSapServiceInstance.createInvoiceWithPO(pInvoice);
				
			}
			else{ 
				//Creating SAP FI Invoice 
				pInvoice = invoiceSapServiceInstance.createInvoiceWithoutPO(pInvoice);
			}
			return invoiceDCTMServiceInstance.updateDctmInvoice(pInvoice);
	}
	
	//Creation and booking of the invoices in status 6
	public void createInvoicesInSAP(){
		List<Invoice> listOfInvoices = invoiceDCTMServiceInstance.retrieveInvoicesByStatus(Integer.parseInt(parametersProperties.getINV_DCTM_STATUS_SIX()));
		for (Invoice invoiceInstance : listOfInvoices) {
			processInvoice(invoiceInstance);	
		}
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
