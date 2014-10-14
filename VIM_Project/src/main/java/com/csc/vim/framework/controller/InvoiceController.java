package com.csc.vim.framework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.model.PurchaseOrder;
import com.csc.vim.framework.properties.RestURIConstants;
import com.csc.vim.framework.service.impl.BusinessService;

@Controller
@ RequestMapping("/process")
public class InvoiceController {

	@Autowired 
	BusinessService businessServiceInstance;
	
	/*
	 * AV2 Process
	 */
	@RequestMapping(value = RestURIConstants.PROCESS_AV2, method = RequestMethod.GET)
	public Invoice processAV2(@RequestParam String r_object_id,@RequestParam String po_number){
		Invoice pInvoice = new Invoice();
		pInvoice.setrObjectId(r_object_id);
		pInvoice.setPurchaseOrder(new PurchaseOrder());
		pInvoice.getPurchaseOrder().setPoNumber(po_number);
		return businessServiceInstance.processAV2(pInvoice);
	}
	
	/*
	 * Getting Invoice SAP Data
	 */
	@RequestMapping(value = RestURIConstants.RETRIEVE_SAP, method = RequestMethod.GET)
	public void retrieveDataFromSAP(@RequestParam String r_object_id){
		Invoice pInvoice = new Invoice();
		pInvoice = businessServiceInstance.getInformationsFromDCTM(r_object_id);
		businessServiceInstance.retrievingSapInformations(pInvoice);
	}
	
	/*
	 * Create SAP Invoice 
	 */
	@RequestMapping(value=RestURIConstants.CREATE_INVOICE, method = RequestMethod.GET)
	public @ResponseBody void CreateInvoice(@RequestParam String r_object_id) {
		Invoice pInvoice = new Invoice();
		pInvoice = businessServiceInstance.getInformationsFromDCTM(r_object_id);
		businessServiceInstance.createInvoiceIntoSAP(pInvoice);
	}
	
	/*
	 * Link DCTM Invoice into SAP
	 */
	@RequestMapping(value=RestURIConstants.LINK_INVOICE, method = RequestMethod.GET)
	public @ResponseBody void synchroniseStatusSevenInvoices(@RequestParam String r_object_id) {
		Invoice pInvoice = new Invoice();
		pInvoice = businessServiceInstance.getInformationsFromDCTM(r_object_id);
		businessServiceInstance.linkInvoiceDctmSap(pInvoice);
	}
	
	/*
	 * process invoice to change invoice status from 6 to 7
	 */
	@RequestMapping(value="/processInvoice", method = RequestMethod.GET)
	public @ResponseBody void processInvoice(@RequestParam String r_object_id) {
		Invoice pInvoice = new Invoice();
		pInvoice.setrObjectId(r_object_id);
		 businessServiceInstance.processInvoice(pInvoice);
	}
	
	/*
	 * Synchronise invoices having status seven 
	 */
	@RequestMapping(value=RestURIConstants.SYNCHRONISE_STATUS_SEVEN, method = RequestMethod.GET)
	public @ResponseBody void synchroniseStatusSevenInvoices() {
		 businessServiceInstance.synchroniseStatusSevenInvoices();
	}
	
	/*
	 * Synchronise invoices having status eight 
	 */
	@RequestMapping(value=RestURIConstants.SYNCHRONISE_STATUS_EIGHT, method = RequestMethod.GET)
	public @ResponseBody void synchroniseStatusEightInvoices() {
		 businessServiceInstance.synchroniseStatusEightInvoices();
	}	
	
}
