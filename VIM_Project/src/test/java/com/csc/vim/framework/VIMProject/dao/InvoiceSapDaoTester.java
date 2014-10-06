package com.csc.vim.framework.VIMProject.dao;


import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.csc.vim.framework.dao.impl.InvoiceSapDao;
import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.model.PurchaseOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
public class InvoiceSapDaoTester{

	@Autowired
	InvoiceSapDao invoiceSapDao;
	
	@Test
	public void testRetieveDataFromSap(){
		Invoice invoiceTester= new Invoice();
		invoiceTester.setrObjectId("0933222203022");
		PurchaseOrder purchaseOrderTester = new PurchaseOrder();
		purchaseOrderTester.setPoNumber("0073400030");
		invoiceTester.setPurchaseOrder(purchaseOrderTester);
		Assert.assertNotNull(invoiceSapDao.retrieveInvoice(invoiceTester));
		
	}
	
//	@Test
//	public void testLinkDocumentToSap(){
//		Invoice invoiceTester= new Invoice();
//		invoiceTester.setrObjectId("0933222203022");
//		PurchaseOrder purchaseOrderTester = new PurchaseOrder();
//		purchaseOrderTester.setPoNumber("73400030");
//		invoiceTester.setPurchaseOrder(purchaseOrderTester);
//		//invoiceSapDaoTester.retrieveInvoice(invoiceTester);
//		Assert.assertNotNull(invoiceSapDao.linkDocument(invoiceTester));
//		
//	}
	
}
