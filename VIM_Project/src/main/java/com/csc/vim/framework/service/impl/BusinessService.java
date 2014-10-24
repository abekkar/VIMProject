package com.csc.vim.framework.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csc.vim.framework.model.ExceptionWrapper;
import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.properties.ErrorConstants;
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
	
	protected final Logger logger = LoggerFactory.getLogger(BusinessService.class);
	
	
	public List<ExceptionWrapper> getInformationsFromDCTM(String r_object_id) {
		// Get Invoice Data from Documentum
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();
		
		Invoice pInvoice = new Invoice();
		pInvoice.setrObjectId(r_object_id);
		try {
			invoiceDCTMServiceInstance.readInvoiceFromDctm(pInvoice);
			logger.debug(ErrorConstants.SUCCESS_DCTM_RETRIEVING_CODE + ErrorConstants.SUCCESS_DCTM_RETRIEVING_MESSAGE);
			return errorListInstance;
		} catch (Exception e) {
			ExceptionWrapper exceptionInstance  = new ExceptionWrapper();
			logger.error(ErrorConstants.ERROR_DCTM_RETRIEVING_CODE +" "+ e.getMessage());
			e.printStackTrace();
			exceptionInstance.setErrorCode(ErrorConstants.ERROR_DCTM_RETRIEVING_CODE);
			exceptionInstance.setMessageCode(e.getMessage());
			errorListInstance.add(exceptionInstance);
			return errorListInstance;
		}
	}
	
	
	public List<ExceptionWrapper> processAV2(Invoice pInvoice){
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();
		
		// Get Invoice Data from Documentum
		try {
			invoiceDCTMServiceInstance.readInvoiceFromDctm(pInvoice);
			logger.debug(ErrorConstants.SUCCESS_DCTM_RETRIEVING_CODE +": "+ ErrorConstants.SUCCESS_DCTM_RETRIEVING_MESSAGE);
		} catch (Exception e) {
			ExceptionWrapper exceptionInstance  = new ExceptionWrapper();
			logger.error(ErrorConstants.ERROR_DCTM_RETRIEVING_CODE +": "+ e.getMessage());
			e.printStackTrace();
			exceptionInstance.setErrorCode(ErrorConstants.ERROR_DCTM_RETRIEVING_CODE);
			exceptionInstance.setMessageCode(e.getMessage());
			errorListInstance.add(exceptionInstance);
			return errorListInstance;
		}
		
		//Get Invoice Data from SAP
		invoiceSapServiceInstance.retrieveDataFromSAP(pInvoice);

		// Check Blocking code
		pInvoice.setBlockingCodeV("T");
		if (pInvoice.getInvoiceFamily() == InvoiceFamilyEnum.TradingCoating.getCategoryId() || pInvoice.getInvoiceFamily() == InvoiceFamilyEnum.ManualFiCoInput.getCategoryId())
		{
			if (pInvoice.getBlockingCodeT() == "F"){
				pInvoice.setBlockingCodeT("T");
			}
		}
		//TODO 
		//delete 3 test lines 
		pInvoice.setScanningDate("29/12/2013 00:00:00");
		pInvoice.setInvoiceCurrency("EUR");
		pInvoice.setInvoiceDate("29/12/2013 00:00:00");
		createInvoiceIntoSAP(pInvoice);
		//TODO
		//Link SAP Document with Documentum object
		//invoiceSapServiceInstance.linkDocumentToSAP(pInvoice);

		return errorListInstance;
		 	
	}
	
	public List<ExceptionWrapper> retrievingSapInformations(Invoice pInvoice){
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();
		if (pInvoice.getrObjectId()!= null)
		{
			List<ExceptionWrapper> errorListInstanceDCTM = new ArrayList<ExceptionWrapper>();
			errorListInstanceDCTM = getInformationsFromDCTM(pInvoice.getrObjectId());
			if (!errorListInstanceDCTM.isEmpty()){
				errorListInstance.addAll(errorListInstanceDCTM);
				return errorListInstance;
			}
			try {
				invoiceDCTMServiceInstance.updateDctmInvoice(invoiceSapServiceInstance.retrieveDataFromSAP(pInvoice));
				logger.debug(ErrorConstants.SUCCESS_DCTM_RETRIEVING_CODE +": "+ ErrorConstants.SUCCESS_DCTM_RETRIEVING_MESSAGE);
			} catch (Exception e) {
				ExceptionWrapper exceptionInstance  = new ExceptionWrapper();
				logger.error(ErrorConstants.ERROR_DCTM_RETRIEVING_CODE +": "+ e.getMessage());
				e.printStackTrace();
				exceptionInstance.setErrorCode(ErrorConstants.ERROR_DCTM_RETRIEVING_CODE);
				exceptionInstance.setMessageCode(e.getMessage());
				errorListInstance.add(exceptionInstance);
				return errorListInstance;
			}
		}
		return errorListInstance;
	}
	
	public List<ExceptionWrapper> createInvoiceIntoSAP(Invoice pInvoice){
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();		
		//The invoice is booked during the creation 
		if (pInvoice.getrObjectId()!= null)
		{
			if ((pInvoice.getInvoiceFamily() == InvoiceCategoryEnum.WhitPoGR.getCategoryId()) || (pInvoice.getInvoiceFamily() == InvoiceCategoryEnum.WhitPO_GR.getCategoryId()) )
					pInvoice = invoiceSapServiceInstance.createInvoiceWithPO(pInvoice);
			else
			{
				pInvoice = invoiceSapServiceInstance.createInvoiceWithoutPO(pInvoice);
			}
			try {
				invoiceDCTMServiceInstance.updateDctmInvoice(pInvoice);
				logger.debug(ErrorConstants.SUCCESS_DCTM_UPDATE_CODE +": "+ ErrorConstants.SUCCESS_DCTM_UPDATE_MESSAGE);
			} catch (Exception e) {
				ExceptionWrapper exceptionInstance  = new ExceptionWrapper();
				logger.error(ErrorConstants.ERROR_DCTM_UPDATING_CODE +": "+ e.getMessage());
				e.printStackTrace();
				exceptionInstance.setErrorCode(ErrorConstants.ERROR_DCTM_UPDATING_CODE);
				exceptionInstance.setMessageCode(e.getMessage());
				errorListInstance.add(exceptionInstance);
				return errorListInstance;
			}
		}
		return errorListInstance;
	}
	
	
	public void linkInvoiceDctmSap(Invoice pInvoice){
		if (pInvoice.getrObjectId()!= null){
			invoiceSapServiceInstance.linkDocumentToSAP(pInvoice);
		}
	}
	
	public List<ExceptionWrapper> processInvoice(Invoice pInvoice){
			if (pInvoice.getInvoiceStatus() == Integer.parseInt(parametersProperties.getINV_DCTM_STATUS_ZERO()))
				pInvoice = invoiceSapServiceInstance.retrieveDataFromSAP(pInvoice);
			return createInvoiceIntoSAP(pInvoice);
	}
	
	//Creation and booking of the invoices in status 6
	public List<ExceptionWrapper> createInvoicesInSAP(){
		List<Invoice> listOfInvoices = null;
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();
			try {
				listOfInvoices = invoiceDCTMServiceInstance.retrieveInvoicesByStatus(Integer.parseInt(parametersProperties.getINV_DCTM_STATUS_SIX()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				ExceptionWrapper exceptionInstance  = new ExceptionWrapper();
				logger.error(ErrorConstants.ERROR_DCTM_RETRIEVING_BY_STATUS_SIX_CODE +": "+ e.getMessage());
				e.printStackTrace();
				exceptionInstance.setErrorCode(ErrorConstants.ERROR_DCTM_RETRIEVING_BY_STATUS_SIX_CODE);
				exceptionInstance.setMessageCode(e.getMessage());
				errorListInstance.add(exceptionInstance);
				return errorListInstance;
			}
		for (Invoice invoiceInstance : listOfInvoices) {
			processInvoice(invoiceInstance);	
		}
		return errorListInstance;
	}
	
	//Create invoice with status 6 in SAP
	public List<ExceptionWrapper> createStatusSixInvoices(){
		List<Invoice> listOfInvoices = null;
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();
		try {
			listOfInvoices = invoiceDCTMServiceInstance.retrieveInvoicesByStatus(Integer.parseInt(parametersProperties.INV_DCTM_STATUS_SIX));
			logger.debug(ErrorConstants.SUCCESS_DCTM_RETRIEVING_BY_STATUS_SIX_CODE +": "+ ErrorConstants.SUCCESS_DCTM_RETRIEVING_BY_STATUS_SIX_MESSAGE);
		} catch (NumberFormatException e2) {
			e2.printStackTrace();
		} catch (Exception e2) {
			ExceptionWrapper exceptionInstance  = new ExceptionWrapper();
			logger.error(ErrorConstants.ERROR_DCTM_RETRIEVING_BY_STATUS_SIX_CODE +": "+ e2.getMessage());
			e2.printStackTrace();
			exceptionInstance.setErrorCode(ErrorConstants.ERROR_DCTM_RETRIEVING_BY_STATUS_SIX_CODE);
			exceptionInstance.setMessageCode(e2.getMessage());
			errorListInstance.add(exceptionInstance);
			return errorListInstance;
		}
		if (null != listOfInvoices)
			for (Invoice invoiceInstance : listOfInvoices) {	
				// Get Invoice Data from Documentum
				try {
					invoiceInstance  = invoiceDCTMServiceInstance.readInvoiceFromDctm(invoiceInstance);
					logger.debug(ErrorConstants.SUCCESS_DCTM_RETRIEVING_CODE+ ErrorConstants.SUCCESS_DCTM_RETRIEVING_MESSAGE);
				} catch (Exception e1) {
					ExceptionWrapper exceptionInstance  = new ExceptionWrapper();
					logger.error(ErrorConstants.ERROR_DCTM_RETRIEVING_CODE + e1.getMessage());
					e1.printStackTrace();
					exceptionInstance.setErrorCode(ErrorConstants.ERROR_DCTM_RETRIEVING_CODE);
					exceptionInstance.setMessageCode(e1.getMessage());
					errorListInstance.add(exceptionInstance);
					return errorListInstance;
				}
				//Creating MM ou FI SAP Invoice
				if ((invoiceInstance.getInvoiceFamily() == InvoiceCategoryEnum.WhitPoGR.getCategoryId()) || (invoiceInstance.getInvoiceFamily() == InvoiceCategoryEnum.WhitPO_GR.getCategoryId()) )
					invoiceInstance = invoiceSapServiceInstance.createInvoiceWithPO(invoiceInstance);
				else
				{
					invoiceInstance = invoiceSapServiceInstance.createInvoiceWithoutPO(invoiceInstance);
				}
				//Updating DCTM Invoice
				try {
					invoiceDCTMServiceInstance.updateDctmInvoice(invoiceInstance);
					logger.debug(ErrorConstants.SUCCESS_DCTM_UPDATE_CODE +": "+ ErrorConstants.SUCCESS_DCTM_UPDATE_MESSAGE);
				} catch (Exception e) {
					ExceptionWrapper exceptionInstance  = new ExceptionWrapper();
					logger.error(ErrorConstants.ERROR_DCTM_UPDATING_CODE +": "+ e.getMessage());
					e.printStackTrace();
					exceptionInstance.setErrorCode(ErrorConstants.ERROR_DCTM_UPDATING_CODE);
					exceptionInstance.setMessageCode(e.getMessage());
					errorListInstance.add(exceptionInstance);
					return errorListInstance;
				}
				//TODO
				//Link SAP Document with Documentum object
				//invoiceSapServiceInstance.linkDocumentToSAP(invoiceInstance);
			}
		return errorListInstance;
	}
		
	//Synchronise the status 7 invoices from SAP
	public List<ExceptionWrapper> synchroniseStatusSevenInvoices(){
		List<Invoice> listOfInvoices = null;
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();
		try {
			listOfInvoices = invoiceDCTMServiceInstance.retrieveInvoicesByStatus(Integer.parseInt(parametersProperties.getINV_DCTM_STATUS_SEVEN()));
			logger.debug(ErrorConstants.SUCCESS_DCTM_RETRIEVING_BY_STATUS_SEVEN_CODE +": "+ ErrorConstants.SUCCESS_DCTM_RETRIEVING_BY_STATUS_SEVEN_MESSAGE);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			ExceptionWrapper exceptionInstance  = new ExceptionWrapper();
			logger.error(ErrorConstants.ERROR_DCTM_RETRIEVING_BY_STATUS_SEVEN_CODE +": "+ e.getMessage());
			exceptionInstance.setErrorCode(ErrorConstants.ERROR_DCTM_RETRIEVING_BY_STATUS_SEVEN_CODE);
			e.printStackTrace();
			exceptionInstance.setMessageCode(e.getMessage());
			errorListInstance.add(exceptionInstance);
			return errorListInstance;
		}
		for (Invoice invoiceInstance : listOfInvoices) {
			invoiceInstance = invoiceSapServiceInstance.retrieveDataFromSAP(invoiceInstance);
			try {
				invoiceDCTMServiceInstance.updateDctmInvoice(invoiceInstance);
				logger.debug(ErrorConstants.SUCCESS_DCTM_UPDATE_CODE +": "+ ErrorConstants.SUCCESS_DCTM_UPDATE_MESSAGE);
			} catch (Exception e) {
				ExceptionWrapper exceptionInstance  = new ExceptionWrapper();
				logger.error(ErrorConstants.ERROR_DCTM_UPDATING_CODE +": "+ e.getMessage());
				e.printStackTrace();
				exceptionInstance.setErrorCode(ErrorConstants.ERROR_DCTM_UPDATING_CODE);
				exceptionInstance.setMessageCode(e.getMessage());
				errorListInstance.add(exceptionInstance);
				return errorListInstance;
			}
		}
		return errorListInstance;
	}
	
	//Synchronise the status 8 invoices from SAP
	public List<ExceptionWrapper> synchroniseStatusEightInvoices(){
		List<Invoice> listOfInvoices = null;
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();
		try {
			listOfInvoices = invoiceDCTMServiceInstance.retrieveInvoicesByStatus(Integer.parseInt(parametersProperties.getINV_DCTM_STATUS_EIGHT()));
			logger.debug(ErrorConstants.SUCCESS_DCTM_RETRIEVING_BY_STATUS_EIGHT_CODE +": "+ ErrorConstants.SUCCESS_DCTM_RETRIEVING_BY_STATUS_EIGHT_MESSAGE);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			ExceptionWrapper exceptionInstance  = new ExceptionWrapper();
			logger.error(ErrorConstants.ERROR_DCTM_RETRIEVING_BY_STATUS_EIGHT_CODE +": "+ e.getMessage());
			e.printStackTrace();
			exceptionInstance.setErrorCode(ErrorConstants.ERROR_DCTM_RETRIEVING_BY_STATUS_EIGHT_CODE);
			exceptionInstance.setMessageCode(e.getMessage());
			errorListInstance.add(exceptionInstance);
			return errorListInstance;
		}
		for (Invoice invoiceInstance : listOfInvoices) {
			invoiceInstance = invoiceSapServiceInstance.retrieveDataFromSAP(invoiceInstance);
			try {
				invoiceDCTMServiceInstance.updateDctmInvoice(invoiceInstance);
				logger.debug(ErrorConstants.SUCCESS_DCTM_UPDATE_CODE +": "+ ErrorConstants.SUCCESS_DCTM_UPDATE_MESSAGE);
			} catch (Exception e) {
				ExceptionWrapper exceptionInstance  = new ExceptionWrapper();
				logger.error(ErrorConstants.ERROR_DCTM_UPDATING_CODE +": "+ e.getMessage());
				e.printStackTrace();
				exceptionInstance.setErrorCode(ErrorConstants.ERROR_DCTM_UPDATING_CODE);
				exceptionInstance.setMessageCode(e.getMessage());
				errorListInstance.add(exceptionInstance);
				return errorListInstance;
			}
		}
		return errorListInstance;
	}
}
