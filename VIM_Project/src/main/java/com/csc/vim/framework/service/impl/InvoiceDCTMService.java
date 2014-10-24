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

@Component
public class InvoiceDCTMService {

	@Autowired
	InvoiceDctmDao invoiceDctmDaoInstance;
	protected final Logger logger = LoggerFactory.getLogger(InvoiceDCTMService.class);
	
	public Invoice readInvoiceFromDctm(Invoice pInvoice) throws DfException, IOException,DfIdentityException,DfAuthenticationException,DfServiceException,DfPrincipalException,Exception {
			return invoiceDctmDaoInstance.retrieveInvoice(pInvoice);
	}
	
	public Invoice updateDctmInvoice(Invoice pInvoice) throws Exception{
			return invoiceDctmDaoInstance.update(pInvoice);
	}
	
	public Invoice updateDctmInvoiceProperties(Invoice pInvoice) throws Exception{
		return invoiceDctmDaoInstance.updateInvoiceProperties(pInvoice);
}
	
	public List<Invoice> retrieveInvoicesByStatus(Integer status) throws Exception{
			return invoiceDctmDaoInstance.getInvoicesByStatut(status);
	}
	
}
