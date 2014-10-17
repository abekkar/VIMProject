package com.csc.vim.framework.dao;

import java.io.IOException;
import java.util.List;

import com.csc.vim.framework.model.Invoice;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;

public interface IInvoiceDctmDao {

	
	/**
	 * @author 	abekkar
	 * @since 	1.0
	 * @param 	pInvoice 		The invoice that we want to get (The invoice doesn't contains all the info yet)
	 * @return 	Invoice 		The input invoice but completed with new informations
	 * @throws 	IOException
	 * @throws 	Exception
	 */
	public Invoice read(Invoice pInvoice) throws  DfException, Exception;


	/**
	 * Update an Invoice into Documentum
	 * 
	 * @author abekkar
	 * @since 1.0
	 * @param pInvoice
	 * @throws Exception
	 */
	public Invoice update(Invoice pInvoice) throws Exception;
	
	/**
	 * If exist, retrieve the informations of the invoice from Documentum
	 * 
	 * @author abekkar	
	 * @apram Invoice object contains the invoice detail to retrieve in DCTM 
	 * @since 1.0
	 * @return Invoice 		The input invoice but completed with new informations
	 * @throws Exception
	 */
	public Invoice retrieveInvoice(Invoice pInvoice) throws Exception;
	
	/**
	 * Get the list of the invoice 
	 * 
	 * @author abekkar	
	 * @param status of the invoice
	 * @since 1.0
	 * @return List<Invoice> 		The list of invoices having the same status as in the input
	 * @throws Exception
	 */
	public List<Invoice> getInvoicesByStatut(Integer status) throws Exception ;
	
	
	public Invoice populateInvoiceProperties(Invoice pInvoice,IDfSession session) throws  DfException;
	
	public Invoice populateInvoiceLines(Invoice pInvoice,IDfSession session) throws  DfException;
	
	public Invoice populateBankInformations(Invoice pInvoice, IDfSession session) throws  DfException;
	
	public Invoice populateApprovers(Invoice pInvoice, IDfSession session) throws  DfException;
	
	public Invoice populateprocessors(Invoice pInvoice, IDfSession session) throws  DfException;
	
	public IDfQuery updateInvoiceProperties(Invoice pInvoice,IDfQuery DQLquery) throws  DfException;
	
	public IDfQuery updateSupplierDetail(Invoice pInvoice, IDfQuery DQLquery,IDfSession session) throws  DfException;
	
	public IDfQuery updatePurchaseOrder(Invoice pInvoice,IDfQuery DQLquery,IDfSession session) throws  DfException;
	
	public IDfQuery updateApprovers(Invoice pInvoice,IDfQuery DQLquery,IDfSession session) throws  DfException;
	
	public IDfQuery updateProcessors(Invoice pInvoice,IDfQuery DQLquery,IDfSession session) throws  DfException;
}
