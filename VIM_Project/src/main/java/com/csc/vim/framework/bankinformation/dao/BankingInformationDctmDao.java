package com.csc.vim.framework.bankinformation.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.stereotype.Repository;

import com.csc.vim.framework.bankinformation.model.BankingInformation;
import com.csc.vim.framework.common.dao.DctmDao;
import com.csc.vim.framework.properties.DctmModelType;
import com.documentum.fc.client.DfAuthenticationException;
import com.documentum.fc.client.DfIdentityException;
import com.documentum.fc.client.DfPrincipalException;
import com.documentum.fc.client.DfServiceException;
import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;

@Repository
public class BankingInformationDctmDao extends DctmDao implements IBankingInformationDao{

	private static final String ACCOUNT_NAME            = "account_name";
	private static final String BANK_NAME               = "bank_name";
	private static final String BANK_IBAN               = "iban";
	
	@Override
	public BankingInformation create(BankingInformation pBankInfo) {
		try {
			IDfSysObject bankInfo = (IDfSysObject) mySession.newObject(DctmModelType.BANKINFO);
			bankInfo.link(pBankInfo.getDctmFolder());
		
			bankInfo.setString(ACCOUNT_NAME, pBankInfo.getAccountName());
			bankInfo.setString(BANK_NAME, pBankInfo.getBankName());
			bankInfo.setString(BANK_IBAN, pBankInfo.getIban());
			
			bankInfo.save();
			
			pBankInfo.setrObjectId(bankInfo.getObjectId().toString());
			
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
		} catch (DfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pBankInfo;
	}


	/**
	 * Get in Dctm a Banking Information Obbject based on its r_object_id
	 * 
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pId	r_object_id  of the BankingInformation object
	 * @return The BankingInformation object in Dctm 
	 */
	@Override
	public BankingInformation getById(String pId) throws DfException {
		IDfPersistentObject sysObject = mySession.getObject(new DfId(pId));
		
		BankingInformation bankInfo = new BankingInformation();
		bankInfo.setAccountName(sysObject.getString(ACCOUNT_NAME));
		bankInfo.setBankName(sysObject.getString(BANK_NAME));
		bankInfo.setIban(sysObject.getString(BANK_IBAN));
		
		return bankInfo;
	}

	/**
	 * Get all the Banking Information objects linked to an object provided in input
	 * 
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pParentId			r_object_id of the parent (for instance the r_object_id of an invoice if we want the its BankingInfo)
	 * @param ptransactionnal	If equals to false, the service will open/close a session
	 * @return List of BankingInformation child of the parent in input (the parent is most likely the invoice)
	 */
	@Override
	public Collection<BankingInformation> getChildrenOfInvoice(String pParentId)
			throws DfException {
		Collection<BankingInformation> result = new ArrayList<BankingInformation>();
		
		// Get the list of supplier children of this invoice (Actualla a lsit of r_object_id)
		Collection<String> listRObjectId = dctmHelper.getChild(mySession, pParentId, DctmModelType.RL_INVOICE_BANKINFO);
		
		// build the supplier object from the r_object_id
		Iterator<String> it = listRObjectId.iterator();
		while(it.hasNext()) {
			String id = it.next();
			BankingInformation bankInfo = getById(id);
			result.add(bankInfo);
		}
		
		return result;
	}


	@Override
	protected void updateJavaBeanWithDctmBean(Object pJavaBean,
			IDfPersistentObject pDctmBean) throws DfException {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void updateDctmBeanWithJavaBean(IDfPersistentObject pDctmBean,
			Object pJavaBean) throws DfException {
		// TODO Auto-generated method stub
		
	}

}
