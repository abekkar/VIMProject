package com.csc.vim.framework.supplier.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csc.vim.framework.invoice.service.InvoiceDctmService;
import com.csc.vim.framework.supplier.dao.ISupplierDAO;
import com.csc.vim.framework.supplier.model.Supplier;
import com.documentum.fc.common.DfException;

/**
 * Provide CRUD Service for supplier
 * 
 * @since 1.0
 * @author syongwaiman2
 *
 */
@Service
public class SupplierDctmService {

	private static final String WORKING_FODLER = "/corp/vim_invoice";
	
	@Autowired
	private InvoiceDctmService invoiceService;
	
	@Autowired
	private ISupplierDAO supplierDAO;
	
	
	/**
	 * Get all the Supplier of an invoice in Dctm
	 * 
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pParentId			r_object_id of the parent (for instance the r_object_id of an invoice if we want the its lines)
	 * @param ptransactionnal	If equals to false, the service will open/close a session
	 * @return
	 */
	public Collection<Supplier> getChildrenOfInvoice(String pParentId, Boolean ptransactionnal) {
		
		Collection<Supplier> childrens = null;
		
		try {
			// Open a session if we are not into a transaction
			if (!ptransactionnal) {
				supplierDAO.openSession();
			}
			
			childrens = supplierDAO.getChildrenOfInvoice(pParentId);
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// make sure to close the dctm Session if we are not into a transaction
			if (!ptransactionnal) {
				try {
					supplierDAO.closeSession();
				} catch (DfException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return childrens;
		
	}
			
	/**
	 * Get a Supplier from Dctm
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pId				r_object_id of the supplier
	 * @param ptransactionnal 	If equals to false, the service will open/close a session
	 * @return Supplier
	 */
	public Supplier getById(String pId, Boolean ptransactionnal) {
		Supplier supplier = null;
		try {
			// Open a session if we are not into a transaction
			if (!ptransactionnal) {
				supplierDAO.openSession();
			}
			
			supplier = supplierDAO.getById(pId);
			
		} catch (DfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			// make sure to close the dctm Session if we are not into a transaction
			if (!ptransactionnal) {
				try {
					supplierDAO.closeSession();
				} catch (DfException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return supplier;
	}
	
	/**
	 * Create a supplier in DCTM
	 * If an invoice is specified, it will be created in the same folder as the invoice
	 * 
	 * @since 1.0
	 * @author syongwaiman2
	 * @param pSupplier			Supplier Object to be created
	 * @throws DfException 
	 * @apram ptransactionnal	True if the execution is part of a transaction (so do not open/close session). 
	 * 							If false, the execution must open&close session
	 */
	public void create(Supplier pSupplier, Boolean ptransactionnal) throws DfException {
		try {
			
			// If no invoice => create in a default folder
			if (pSupplier.getDctmFolder() == null) {
				pSupplier.setDctmFolder(WORKING_FODLER);
			}
			
			// Open a session if we are not into a transaction
			if (!ptransactionnal)
				supplierDAO.openSession();
			
			// Create the supplier
			supplierDAO.create(pSupplier);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// make sure to close the dctm Session if we are not into a transaction
			if (!ptransactionnal) {
				supplierDAO.closeSession();
			}
		}
		
	
	}
	
}
