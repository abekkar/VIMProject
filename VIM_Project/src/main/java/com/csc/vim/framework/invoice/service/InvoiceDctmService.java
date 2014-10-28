package com.csc.vim.framework.invoice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csc.vim.framework.bankinformation.model.BankingInformation;
import com.csc.vim.framework.invoice.dao.InvoiceDodcumentumDao;
import com.csc.vim.framework.invoice.model.Invoice;
import com.csc.vim.framework.model.PurchaseOrder;
import com.csc.vim.framework.properties.DctmModelType;
import com.csc.vim.framework.supplier.model.Supplier;
import com.csc.vim.framework.util.FileUtils;
import com.documentum.fc.client.DfAuthenticationException;
import com.documentum.fc.client.DfIdentityException;
import com.documentum.fc.client.DfPrincipalException;
import com.documentum.fc.client.DfServiceException;
import com.documentum.fc.common.DfException;

@Service
public class InvoiceDctmService {

	@Autowired
	private InvoiceDodcumentumDao invoiceDao;
	
	@Autowired
	private FileUtils fileUtils;
	
	public Invoice getById(String pId, Boolean pTransaction) {
		Invoice invoice = null;
		try {			
			// Open a session if we are not into a transaction
			if (!pTransaction) {
				invoiceDao.openSession();
			}
			invoice = invoiceDao.getById(pId);
		}
		catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} finally {
			if (!pTransaction) {
				try {
					invoiceDao.closeSession();
				} catch (DfException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return invoice;
	}
	
	/**
	 * From the HTTP content file + the metadata of the invoice, create a full content+metadata invoice into DCTM
	 * Objet linked to the invoice is not created. 
	 * To create the invoice + all his linked objet, see the InvoiceBuilder (Factory)
	 * Once the invoice is created, the HTTP content file is deleted
	 * 
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pInvoice
	 * @return
	 * @throws DfException 
	 */
	public Invoice create(Invoice pInvoice, Boolean pTransaction) {
		try {
			
			// Open a session if we are not into a transaction
			if (!pTransaction) {
				invoiceDao.openSession();
			}
			
			// Create the content invoice object into DCTM
			invoiceDao.create(pInvoice);
			
		} catch (DfIdentityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (DfAuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (DfPrincipalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (DfServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (DfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if (!pTransaction) {
				try {
					invoiceDao.closeSession();
				} catch (DfException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// Delete the HTTP File
			fileUtils.deleteFile(pInvoice.getContent());
		}
		return pInvoice;
	}
	
	
	/**
	 * attach a Purchase Order (Java level + Dctm level) to an invoice
	 * 
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pInvoice
	 * @param poType
	 * @param pPo
	 * @return r_object_id of the dm_relation created
	 * @throws DfException 
	 */
	public String linkPurchaseOrder(Invoice pInvoice, PurchaseOrder pPo) throws DfException {
		// Link it at the Java Level
		pInvoice.setPurchaseOrder(pPo);
		
		return linkPurchaseOrder(pInvoice.getrObjectId(), pPo);
	}
	
	/**
	 * Create a link in Documentum between the  an object (supposely an invoice) and the purchase order
	 * 
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pInvoiceDctmId
	 * @param pPo
	 * @return r_object_id of the dm_relation created
	 * @throws DfException
	 */
	public String linkPurchaseOrder(String pInvoiceDctmId, PurchaseOrder pPo) throws DfException {		
		// Link it at the Dctm Level
		return invoiceDao.linkPurchaseOrder(pInvoiceDctmId, DctmModelType.PURCHASEORDER, pPo.getrObjectId());
	}
	
	
	/**
	 * Attach a supplier (Java level + Dctm level) to an invoice
	 * 
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pInvoice
	 * @param pSupplier
	 * @return r_object_id of the dm_relation created
	 */
	public String linkSupplier(Invoice pInvoice, Supplier pSupplier) {
		// Link it at the Java Level
		pInvoice.setSupplierDetail(pSupplier);
		
		// Link it at the Dctm Level
		return invoiceDao.linkSupplier(pInvoice.getrObjectId(), pSupplier.getrObjectId());		
	}
	
	/**
	 * Attach a list of BankingInformation (Java level + Dctm level) to an invoice
	 * 
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pInvoice
	 * @param pSupplier
	 * @return r_object_id of the dm_relation created
	 */
	public void linkBankingInformation(Invoice pInvoice, Collection<BankingInformation> pBankInfo) {
		// Link it at the Java Level
		pInvoice.setBankingInformation(pBankInfo);
		
		// transform the array of object into an array of String
		Collection<String> listStr = new ArrayList<String>();
		Iterator<BankingInformation> it = pBankInfo.iterator();
		while (it.hasNext()) {
			BankingInformation bankInfo = it.next();
			listStr.add(bankInfo.getrObjectId());
		}
		
		// Link it at the Dctm Level
		invoiceDao.linkBankingInformation(pInvoice.getrObjectId(), listStr);		
	}
}
