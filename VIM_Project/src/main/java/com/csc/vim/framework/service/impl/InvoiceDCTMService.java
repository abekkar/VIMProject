package com.csc.vim.framework.service.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.csc.vim.framework.dao.impl.InvoiceDctmDao;
import com.csc.vim.framework.model.Invoice;
import com.documentum.fc.client.DfAuthenticationException;
import com.documentum.fc.client.DfIdentityException;
import com.documentum.fc.client.DfPrincipalException;
import com.documentum.fc.client.DfServiceException;
import com.documentum.fc.common.DfException;
import com.sap.conn.jco.JCoException;

@Component
public class InvoiceDCTMService {

	@Autowired
	InvoiceDctmDao invoiceDctmDaoInstance;
	protected final Logger logger = LoggerFactory.getLogger(InvoiceDCTMService.class);	
	public Invoice readInvoiceFromDctm(Invoice pInvoice){
		try {
			return invoiceDctmDaoInstance.retrieveInvoice(pInvoice);
		} catch (Exception e) {
			logger.error(e.getMessage()); 
		}
		return null;
	}
	
	public Invoice updateDctmInvoice(Invoice pInvoice){
		try {
			return invoiceDctmDaoInstance.update(pInvoice);
		} catch (Exception e) {
			logger.error(e.getMessage()); 
		}
		return null;
	}
	
	public List<Invoice> retrieveInvoicesByStatus(Integer status){
		try {
			return invoiceDctmDaoInstance.getInvoicesByStatut(status);
		} catch (DfIdentityException e) {
			logger.error(e.getMessage()); 
		} catch (DfAuthenticationException e) {
			logger.error(e.getMessage()); 
		} catch (DfPrincipalException e) {
			logger.error(e.getMessage()); 
		} catch (DfServiceException e) {
			logger.error(e.getMessage()); 
		} catch (DfException e) {
			logger.error(e.getMessage()); 
		} catch (JCoException e) {
			logger.error(e.getMessage()); 
		} catch (IOException e) {
			logger.error(e.getMessage()); 
		}
		return null;
	}
	
}
