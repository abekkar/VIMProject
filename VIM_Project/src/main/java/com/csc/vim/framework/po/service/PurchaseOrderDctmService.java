package com.csc.vim.framework.po.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csc.vim.framework.invoice.service.InvoiceDocumentumService;
import com.csc.vim.framework.model.PurchaseOrder;
import com.csc.vim.framework.po.dao.PurchaseOrderDAODctm;
import com.documentum.fc.client.DfAuthenticationException;
import com.documentum.fc.client.DfIdentityException;
import com.documentum.fc.client.DfPrincipalException;
import com.documentum.fc.client.DfServiceException;
import com.documentum.fc.common.DfException;

/**
 * Provide CRUD Service for DCTM Purchase Order
 * @since 1.0
 * @author syongwaiman2
 *
 */
@Service
public class PurchaseOrderDctmService {

	private static final String WORKING_FODLER = "/corp/vim_invoice";
	
	@Autowired
	private PurchaseOrderDAODctm poDaoDctm;
	
	@Autowired
	private InvoiceDocumentumService invoiceService;
	
	public PurchaseOrder getById(String pId, Boolean pTransactionnal) throws DfException {
		PurchaseOrder po = null;
		
		try {
			// Open a session if we are not into a transaction
			if (!pTransactionnal)
				poDaoDctm.openSession();
			
			poDaoDctm.getById(pId);
		} catch (DfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (!pTransactionnal)
				poDaoDctm.closeSession();
		}
		
		return po;
	}
	
	/**
	 * Create an PurchaseOrder Business Object in Documentum
	 * If this PO is linked with an invoice
	 * 		- The PO will be in the same folder as its invoice
	 * If there is no invoice
	 * 		- by default it will be put in the VIM working folder
	 * Note: To attach a PO with an Invoice, see the InvoiceService.
	 * @since 1.0
	 * @author syongwaiman2
	 * @param pPo				the Purchase Order to be created in Dctm
	 * @param ptransactionnal	True if the execution is part of a transaction (so do not open/close session). 
	 * 							If false, the execution must open&close session
	 * @throws DfException 
	 */
	public void create(PurchaseOrder pPo, Boolean pTransactionnal) throws DfException {
		
		try {
			// By default, the PO is created into the same folder as the invoice
			// If no invoice => create in a default folder
			if (pPo.getDctmFolder() == null) {
				pPo.setDctmFolder(WORKING_FODLER);
			}
			
			// Open a session if we are not into a transaction
			if (!pTransactionnal) {
				poDaoDctm.openSession();
			}
			
			// Create the PO
			poDaoDctm.create(pPo);
			
			// if an invoice is mentionned, link this po with the invoice
			if (pPo.getParentId() != null) {
				invoiceService.linkPurchaseOrder(pPo.getParentId(), pPo);
			}
			
		} catch (DfIdentityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DfAuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DfPrincipalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DfServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (!pTransactionnal)
				poDaoDctm.closeSession();
		}
	}
	
	/**
	 * Get all the PurchaseOrder of an invoice in Dctm
	 * 
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pParentId			r_object_id of the parent (for instance the r_object_id of an invoice if we want the its lines)
	 * @param ptransactionnal	If equals to false, the service will open/close a session
	 * @return
	 */
	public Collection<PurchaseOrder> getChildrenOfInvoice(String pParentId, Boolean ptransactionnal) {
		
		Collection<PurchaseOrder> childrens = null;
		
		try {
			// Open a session if we are not into a transaction
			if (!ptransactionnal) {
				poDaoDctm.openSession();
			}
			
			childrens = poDaoDctm.getChildrenOfInvoice(pParentId);			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// make sure to close the dctm Session if we are not into a transaction
			if (!ptransactionnal) {
				try {
					poDaoDctm.closeSession();
				} catch (DfException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return childrens;		
	}
}
