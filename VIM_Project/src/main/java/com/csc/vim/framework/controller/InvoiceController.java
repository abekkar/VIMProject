package com.csc.vim.framework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.model.PurchaseOrder;
import com.csc.vim.framework.service.BusinessService;

@Controller
@ RequestMapping("/process")
public class InvoiceController {

	@Autowired 
	BusinessService businessServiceInstance;
	
	/*
	 * AV2 Process
	 */
	@RequestMapping(value = "/V1/SAV2/{r_object_id}", method = RequestMethod.GET)
	public Invoice processAV2(@PathVariable String r_object_id){
		Invoice pInvoice = new Invoice();
		pInvoice.setrObjectId(r_object_id);
		pInvoice.setPurchaseOrder(new PurchaseOrder());
//		pInvoice.getPurchaseOrder().setPoNumber(po_number);
		return businessServiceInstance.processAV2(pInvoice);
	}
	
	/*
	 * Getting Invoice SAP Data
	 */
	@RequestMapping(value = "/retrieveInfosFromSAP", method = RequestMethod.GET)
	public void retrieveDataFromSAP(Invoice pInvoice){
		businessServiceInstance.retrievingSapInformations(pInvoice);
	}
	
	/*
	 * Synchronise invoices having status eight 
	 */
	@RequestMapping(value="/SyncEightStatus", method = RequestMethod.GET)
	public @ResponseBody void synchroniseStatusEightInvoices() {
		 businessServiceInstance.synchroniseStatusEightInvoices();
	}
	
	/*
	 * Synchronise invoices having status seven 
	 */
	@RequestMapping(value="/SyncSevenStatus", method = RequestMethod.GET)
	public @ResponseBody void synchroniseStatusSevenInvoices() {
		 businessServiceInstance.synchroniseStatusSevenInvoices();
	}
	
	/*
	 * Link DCTM Invoice into SAP
	 */
	@RequestMapping(value="/linkInvoice/{r_object_id}", method = RequestMethod.GET)
	public @ResponseBody void synchroniseStatusSevenInvoices(@PathVariable String r_object_id) {
		Invoice pInvoice = new Invoice();
		pInvoice.setrObjectId(r_object_id);
		 businessServiceInstance.linkInvoiceDctmSap(pInvoice);
	}
	
	/*
	 * Create SAP Invoice 
	 */
	@RequestMapping(value="/createInvoice/{r_object_id}", method = RequestMethod.GET)
	public @ResponseBody void CreateInvoice(@PathVariable String r_object_id) {
		Invoice pInvoice = new Invoice();
		pInvoice.setrObjectId(r_object_id);
		 businessServiceInstance.createInvoiceIntoSAP(pInvoice);
	}
	
	/*
	 * process invoice to change invoice status from 6 to 7
	 */
	@RequestMapping(value="/processInvoice/{r_object_id}", method = RequestMethod.GET)
	public @ResponseBody void processInvoice(@PathVariable String r_object_id) {
		Invoice pInvoice = new Invoice();
		pInvoice.setrObjectId(r_object_id);
		 businessServiceInstance.processInvoice(pInvoice);
	}
	
}
