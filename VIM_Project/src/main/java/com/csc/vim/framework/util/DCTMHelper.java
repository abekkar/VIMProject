package com.csc.vim.framework.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.csc.vim.framework.properties.Parameters;
import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.DfAuthenticationException;
import com.documentum.fc.client.DfIdentityException;
import com.documentum.fc.client.DfPrincipalException;
import com.documentum.fc.client.DfServiceException;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfGroup;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.IDfUser;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfLoginInfo;

@Component
public class DCTMHelper {
	protected final Logger logger = LoggerFactory.getLogger(DCTMHelper.class);

	@Autowired
	protected DateUtils dateUtils;
	private String cRepositoryName;
	private String cUserName;
	private String cUserPassword;
	private static final String GROUP_PREFIX = "G_APP_"; 
	private IDfSessionManager cSessionManager;

	@Autowired
	protected Parameters parametersPropertiesInstance;

	public IDfSession getSession() throws DfIdentityException, DfAuthenticationException, DfPrincipalException, DfServiceException {
		IDfSession result = null;

		if (cSessionManager == null) {
			init();
		}
		result = cSessionManager.getSession(cRepositoryName);

		return result;
	}

	public void closeSession(IDfSession p_session) throws DfException {
		logger.debug("Closing DCTM Connection");
		cSessionManager.release(p_session);
	}

	protected void init() {
		cRepositoryName = parametersPropertiesInstance.getDctmRepository();
		cUserName = parametersPropertiesInstance.getDctmUsername();
		cUserPassword = parametersPropertiesInstance.getDctmPassword();

		logger.debug("Opening DCTM Connection");
		try {
			cSessionManager = createSessionManager();

			addIdentity(cUserName, cUserPassword, cRepositoryName);
		} catch (DfException e1) {
			e1.printStackTrace();
		}

	}

	private IDfSessionManager createSessionManager() throws DfException {
		logger.debug("Creating Session Manager");
		IDfClientX clientX = new DfClientX();
		IDfClient localClient = clientX.getLocalClient();
		IDfSessionManager sessMgr = localClient.newSessionManager();
		return sessMgr;
	}
	
	private void releaseSession(IDfSession session){
		cSessionManager.release(session);
	}

	private void addIdentity(String p_username, String p_password,
			String p_repositoryName) throws DfException {
		logger.debug("Creating a new ClientX");

		IDfClientX clientX = new DfClientX();

		IDfLoginInfo loginInfo = clientX.getLoginInfo();
		loginInfo.setUser(p_username);
		loginInfo.setPassword(p_password);
		loginInfo.setDomain(null);

		// check if session manager already has an identity.
		// if yes, remove it.
		if (cSessionManager.hasIdentity(p_repositoryName)) {
			logger.debug("Clearing the identify in the repository: " + p_repositoryName);
			cSessionManager.clearIdentity(p_repositoryName);
		}

		logger.debug("Set the identity in the repository: " + p_repositoryName + " for user: " + p_username + " with: " + p_password);
		cSessionManager.setIdentity(p_repositoryName, loginInfo);
	}
	
	/**
	 * Method to create a new group depending on the invoice name and and list of users
	 * @author abekkar
	 * @param String invoiceName listUsers(IdfUser)
	 * @return boolean true if the group is created, else false
	 * @since 1.0
	 */
	public boolean createGroupForInvoice(String invoiceName,List<IDfUser> listUsers ){
		IDfGroup valGroup;
		IDfSession session = null;
		try
        {
			session = getSession();
			valGroup = (IDfGroup) session.newObject("dm_group");
			valGroup.setGroupName(GROUP_PREFIX+invoiceName);
			valGroup.setGroupClass("group");
			valGroup.setDescription("Group approver for the invoice:"+invoiceName);
			for (IDfUser idfUser : listUsers) {
				// Using the login Name for unicity
				valGroup.addUser(idfUser.getUserLoginName());
			}	
			valGroup.save();
			return true;
        } catch (DfIdentityException e) {
			e.printStackTrace();
		} catch (DfAuthenticationException e) {
			e.printStackTrace();
		} catch (DfPrincipalException e) {
			e.printStackTrace();
		} catch (DfServiceException e) {
			e.printStackTrace();
		} catch (DfException e) {
			e.printStackTrace();
		}
		finally
        {
			releaseSession(session);
        }
		
		return false;
	}
	
	/**
	 * Method to remove a new group depending on the invoice name and and list of users
	 * @author abekkar
	 * @since 1.0
	 * @param String invoiceGroupName
	 * @return boolean true if the group is deleted, else false
	 */
	public boolean deleteInvoiceGroup(String invoiceGroupName){
		IDfGroup valGroup;
		IDfSession session = null;
		try
        {
			session = getSession();
			valGroup = (IDfGroup) session.getGroup(invoiceGroupName);
			valGroup.destroy();
			return true;
        } catch (DfIdentityException e) {
			e.printStackTrace();
		} catch (DfAuthenticationException e) {
			e.printStackTrace();
		} catch (DfPrincipalException e) {
			e.printStackTrace();
		} catch (DfServiceException e) {
			e.printStackTrace();
		} catch (DfException e) {
			e.printStackTrace();
		}
		finally
        {
			releaseSession(session);
        }
		
		return false;
	}
	
	
	public String getRepositoryName(){
		return getRepositoryName();
	}
	


}
