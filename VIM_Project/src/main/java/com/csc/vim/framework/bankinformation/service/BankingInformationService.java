package com.csc.vim.framework.bankinformation.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csc.vim.framework.bankinformation.dao.BankingInformationDctmDao;
import com.csc.vim.framework.bankinformation.model.BankingInformation;
import com.csc.vim.framework.service.impl.InvoiceDctmService;
import com.documentum.fc.client.DfAuthenticationException;
import com.documentum.fc.client.DfIdentityException;
import com.documentum.fc.client.DfPrincipalException;
import com.documentum.fc.client.DfServiceException;
import com.documentum.fc.common.DfException;

@Service
public class BankingInformationService {

	private static final String WORKING_FODLER = "/corp/vim_invoice";
	
	@Autowired
	private BankingInformationDctmDao bankingInfoDao;
	
	@Autowired
	private InvoiceDctmService invoiceService;
	
	public BankingInformation getById(String pId, Boolean pTransactionnal) throws DfException {
		BankingInformation bankingInfo = null;
		
		try {
			// Open a session if we are not into a transaction
			if (!pTransactionnal)
				bankingInfoDao.openSession();
			
			bankingInfoDao.getById(pId);
		} catch (DfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (!pTransactionnal)
				bankingInfoDao.closeSession();
		}
		
		return bankingInfo;
	}
	
	/**
	 * Create an BankingInformation Business Object in Documentum
	 * If this Object is linked with an invoice
	 * 		- The Object will be in the same folder as its invoice
	 * If there is no invoice
	 * 		- by default it will be put in the VIM working folder
	 * Note: To attach a PO with an Invoice, see the InvoiceService.
	 * @since 1.0
	 * @author syongwaiman2
	 * @param pBankingInfo		the BankingInformation to be created in Dctm
	 * @param ptransactionnal	True if the execution is part of a transaction (so do not open/close session). 
	 * 							If false, the execution must open&close session
	 * @throws DfException 
	 */
	public void create(BankingInformation pBankingInfo, Boolean pTransactionnal) throws DfException {
		
		try {
			// By default, the Object is created into the same folder as the invoice
			// If no invoice => create in a default folder
			if (pBankingInfo.getDctmFolder() == null) {
				pBankingInfo.setDctmFolder(WORKING_FODLER);
			}
			
			// Open a session if we are not into a transaction
			if (!pTransactionnal) {
				bankingInfoDao.openSession();
			}
			
			// Create the PO
			bankingInfoDao.create(pBankingInfo);
			
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
				bankingInfoDao.closeSession();
		}
	}
	
	/**
	 * Get all the Bankinginformation of an invoice in Dctm
	 * 
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pParentId			r_object_id of the parent (for instance the r_object_id of an invoice if we want the its bankingInfo)
	 * @param ptransactionnal	If equals to false, the service will open/close a session
	 * @return
	 */
	public Collection<BankingInformation> getChildrenOfInvoice(String pParentId, Boolean ptransactionnal) {
		
		Collection<BankingInformation> childrens = null;
		
		try {
			// Open a session if we are not into a transaction
			if (!ptransactionnal) {
				bankingInfoDao.openSession();
			}
			
			childrens = bankingInfoDao.getChildrenOfInvoice(pParentId);			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// make sure to close the dctm Session if we are not into a transaction
			if (!ptransactionnal) {
				try {
					bankingInfoDao.closeSession();
				} catch (DfException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return childrens;		
	}
}
