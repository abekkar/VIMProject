package com.csc.vim.framework.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.model.PurchaseOrder;
import com.csc.vim.framework.service.impl.InvoiceSapService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
public class InvoiceSapServiceTester {
	@Autowired
	InvoiceSapService invoiceSapServiceInstance;
	
	@Test
	public void retrieveSAPData(){
		Invoice invoiceInstance = new Invoice();
		PurchaseOrder purchaseOrderInstance = new PurchaseOrder();
		purchaseOrderInstance.setPoNumber("0073400030");
		purchaseOrderInstance.setPoNumberPosition("00000");
		invoiceInstance.setPurchaseOrder(purchaseOrderInstance);
		invoiceInstance.setInvoiceNetAmount("0.00");
		Assert.assertNotNull(invoiceSapServiceInstance.retrieveDataFromSAP(invoiceInstance));
	}
	
}
