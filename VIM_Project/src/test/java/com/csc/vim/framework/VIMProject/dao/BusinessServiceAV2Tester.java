package com.csc.vim.framework.VIMProject.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.model.PurchaseOrder;
import com.csc.vim.framework.service.impl.BusinessService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
public class BusinessServiceAV2Tester {

	@Autowired
	BusinessService businessServiceInstance;
	
	@Test
	public void testProcessAV2(){
		Invoice invoiceInstance = new Invoice();
		invoiceInstance.setrObjectId("0933222203022");
		PurchaseOrder purchaseOrderTester = new PurchaseOrder();
		purchaseOrderTester.setPoNumber("73400030");
		invoiceInstance.setPurchaseOrder(purchaseOrderTester);
		businessServiceInstance.processAV2(invoiceInstance);
	}
}
