package com.csc.vim.framework.dao;

import java.io.IOException;

import com.csc.vim.framework.model.Invoice;

public interface IInvoiceDctmDao {

	
	/**
	 * @author 	abekkar
	 * @since 	1.0
	 * @param 	pInvoice 		The invoice that we want to get (The invoice doesn't contains all the info yet)
	 * @return 	Invoice 		The input invoice but completed with new informations
	 * @throws 	IOException
	 * @throws 	Exception
	 */
	public Invoice read(Invoice pInvoice) throws  Exception;


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
	
	
	
	
}
