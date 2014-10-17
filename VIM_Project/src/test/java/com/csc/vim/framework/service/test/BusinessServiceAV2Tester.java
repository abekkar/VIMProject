package com.csc.vim.framework.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.csc.vim.framework.model.ExceptionWrapper;
import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.service.impl.BusinessService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
@SuppressWarnings("unused")
public class BusinessServiceAV2Tester {
	private static final String r_object_id = "090d8b66800135cd";
	@Autowired
	BusinessService businessServiceInstance;
	
	@Test
	public void testProcessAV2(){
		Invoice invoiceInstance = new Invoice();
		invoiceInstance.setrObjectId(r_object_id);
		businessServiceInstance.processAV2(invoiceInstance);
	}
	
	@Test
	public void testGetInformationsFromDCTM(){
		Invoice invoiceInstance = new Invoice();
		
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();
		invoiceInstance.setrObjectId(r_object_id);
		errorListInstance = businessServiceInstance.getInformationsFromDCTM(invoiceInstance.getrObjectId());
	}
	
	@Test
	public void testRetrievingSapInformations(){
		Invoice invoiceInstance = new Invoice();
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();
		invoiceInstance.setrObjectId(r_object_id);
		errorListInstance = businessServiceInstance.retrievingSapInformations(invoiceInstance);
	}
	
	@Test
	public void testCreateInvoiceIntoSAP(){
		Invoice invoiceInstance = new Invoice();
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();
		invoiceInstance.setrObjectId(r_object_id);
		errorListInstance = businessServiceInstance.createInvoiceIntoSAP(invoiceInstance);
	}
	
	@Test
	public void testProcessInvoice(){
		Invoice invoiceInstance = new Invoice();
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();
		invoiceInstance.setrObjectId(r_object_id);
		errorListInstance = businessServiceInstance.processInvoice(invoiceInstance);
	}
	
	@Test
	public void testCreateInvoicesInSAP(){
		Invoice invoiceInstance = new Invoice();
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();
		invoiceInstance.setrObjectId(r_object_id);
		errorListInstance = businessServiceInstance.createInvoicesInSAP();
	}
	
	@Test
	public void testCreateStatusSixInvoices(){
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();
		errorListInstance = businessServiceInstance.createStatusSixInvoices();
	}
	
	@Test
	public void testCreateStatusSevenInvoices(){
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();
		errorListInstance = businessServiceInstance.synchroniseStatusSevenInvoices();
	}
	
	@Test
	public void testCreateStatusEightInvoices(){
		List<ExceptionWrapper> errorListInstance = new ArrayList<ExceptionWrapper>();
		errorListInstance = businessServiceInstance.synchroniseStatusEightInvoices();
	}
	
}
