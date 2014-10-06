package com.csc.vim.framework.service;

import java.io.IOException;
import java.util.List;

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
	
	public Invoice readInvoiceFromDctm(Invoice pInvoice){
		try {
			return invoiceDctmDaoInstance.retrieveInvoice(pInvoice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Invoice updateDctmInvoice(Invoice pInvoice){
		try {
			return invoiceDctmDaoInstance.update(pInvoice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Invoice> retrieveInvoicesByStatus(Integer status){
		try {
			return invoiceDctmDaoInstance.getInvoicesByStatut(status);
		} catch (DfIdentityException e) {
			e.printStackTrace();
		} catch (DfAuthenticationException e) {
			e.printStackTrace();
		} catch (DfPrincipalException e) {
			e.printStackTrace();
		} catch (DfServiceException e) {
			e.printStackTrace();
		} catch (DfException e) {
			e.printStackTrace();
		} catch (JCoException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
