package com.csc.vim.framework.VIMProject.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.csc.vim.framework.model.ExceptionWrapper;
import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.model.PurchaseOrder;
import com.csc.vim.framework.service.impl.BusinessService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
public class InvoiceDctmDaoTester {

	@Autowired
	BusinessService businessServiceInstance;
	
	@Test
	public List<ExceptionWrapper> readFromDctm(){
		Invoice invoiceTester= new Invoice();
		invoiceTester.setrObjectId("090d8b6680005edf");
		return businessServiceInstance.retrievingSapInformations(invoiceTester);
	}
	
//	@Test
//	public void updateIntoDctm(){
//		Invoice invoiceTester= new Invoice();
//		invoiceTester.setrObjectId("090d8b6680005edf");
//		invoiceTester.setBlockingCodeT("T");
//		PurchaseOrder purchaseOrderTester = new PurchaseOrder();
//		purchaseOrderTester.setPoNumber("0073400030");
//		invoiceTester.setPurchaseOrder(purchaseOrderTester);
//		try {
//			//invoiceDctmDaoTester.update(invoiceTester);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
