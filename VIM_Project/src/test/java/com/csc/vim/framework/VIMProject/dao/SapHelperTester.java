package com.csc.vim.framework.VIMProject.dao;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.beans.factory.annotation.Autowired;

import com.csc.vim.framework.util.SapHelper;
import com.sap.conn.jco.JCoException;

public class SapHelperTester extends TestCase{


	
	public static Test suite()
    {
        return new TestSuite( SapHelperTester.class );
    }
	
	public void testConnection(){
		try {
			assertNotNull(SapHelper.connect());
		} catch (JCoException e) {
			e.printStackTrace();
		}
		
	}
}
