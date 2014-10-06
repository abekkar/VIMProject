package com.csc.vim.framework.VIMProject.dao;

import java.io.IOException;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.csc.vim.framework.dao.impl.InvoiceDctmDao;
import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.model.PurchaseOrder;
import com.sap.conn.jco.JCoException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
public class InvoiceDctmDaoTester {

	@Autowired
	InvoiceDctmDao invoiceDctmDaoTester;
	
	@Test
	public void connectToDctm(){
		Invoice invoiceTester= new Invoice();
		//090d8bcb80028aff
		//VMDSCAN201406051159-005
		//090d8bcb80056b09
		invoiceTester.setrObjectId("090d8b6680005edf");
		//PurchaseOrder purchaseOrderTester = new PurchaseOrder();
		//purchaseOrderTester.setPoNumber("0073400030");
		//invoiceTester.setPurchaseOrder(purchaseOrderTester);
		try {
			invoiceDctmDaoTester.read(invoiceTester);
		} catch (JCoException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
