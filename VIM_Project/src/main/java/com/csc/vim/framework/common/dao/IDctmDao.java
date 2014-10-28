package com.csc.vim.framework.common.dao;

import java.util.Collection;

import com.csc.vim.framework.common.model.JipSession;
import com.documentum.fc.client.DfAuthenticationException;
import com.documentum.fc.client.DfIdentityException;
import com.documentum.fc.client.DfPrincipalException;
import com.documentum.fc.client.DfServiceException;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;

public interface IDctmDao<BusinessObject> {

	public JipSession<IDfSession> openSession() throws DfIdentityException, DfAuthenticationException, DfPrincipalException, DfServiceException;
	
	public void closeSession() throws DfException;
	
	public BusinessObject create(BusinessObject pBusinessObject) throws DfException;
	
	public BusinessObject getById(String pId) throws DfException;
	
	public Collection<BusinessObject> getChildrenOfInvoice(String pParentId) throws DfException;
}
