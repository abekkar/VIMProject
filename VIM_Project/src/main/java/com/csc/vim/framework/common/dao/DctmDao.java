package com.csc.vim.framework.common.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.csc.vim.framework.common.model.JipDctmSession;
import com.csc.vim.framework.common.model.JipSession;
import com.csc.vim.framework.util.DctmHelper;
import com.documentum.fc.client.DfAuthenticationException;
import com.documentum.fc.client.DfIdentityException;
import com.documentum.fc.client.DfPrincipalException;
import com.documentum.fc.client.DfServiceException;
import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;

/**
 * The purpose of this Class is to make all DCTM DAO benefit a open/close connection
 * These open/close connection will be called by Services that orchestrates connection/transaction
 * 
 * @since 1.0
 * @author syongwaiman2
 *
 */
public abstract class DctmDao<T>  {

	@Autowired
	protected DctmHelper dctmHelper;
	
	protected IDfSession mySession = null;
	
	protected String dctmBusinessObjectTypeName = null;

	//private Class< T > businessObject;
	//protected org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(businessObject);
	
	/**
	 * Close a DCTM Connection
	 * @sicne 1.0
	 * @author syongwaiman2
	 * @throws DfException
	 */
	public void closeSession() throws DfException {
		dctmHelper.closeSession(mySession);
	}

	/**
	 * Open a DCTM Connection
	 * @since 1.0
	 * @author syongwaiman2
	 * @throws DfIdentityException
	 * @throws DfAuthenticationException
	 * @throws DfPrincipalException
	 * @throws DfServiceException
	 * @return Documentum SEssion (IDfSEssion)
	 */
	public  JipSession<IDfSession> openSession() throws DfIdentityException,
			DfAuthenticationException, DfPrincipalException, DfServiceException {
		mySession = dctmHelper.getSession();
		
		JipSession<IDfSession> jipSession = new JipDctmSession(mySession);
		
		return jipSession;
	
	}
	
	/**
	 * Session Setter
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pSession
	 */
	public void setSession(JipSession<IDfSession> pSession) {
		mySession = pSession.getSession();
	}
	
	
	
	protected abstract void updateJavaBeanWithDctmBean(T pJavaBean, IDfPersistentObject pDctmBean) throws DfException;
	
	protected abstract void updateDctmBeanWithJavaBean(IDfPersistentObject pDctmBean, T pJavaBean) throws DfException;
}
