package com.csc.vim.framework.common.model;

import com.documentum.fc.client.IDfSession;


public class JipDctmSession extends JipSession<IDfSession> {

	private IDfSession session;
	
	public JipDctmSession(IDfSession pSession) {
		session = pSession;
	}
	

	@Override
	public IDfSession getSession() {
		return session;
	}


}
