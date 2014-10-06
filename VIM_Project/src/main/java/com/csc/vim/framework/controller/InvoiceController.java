package com.csc.vim.framework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.service.BusinessService;

@Controller
@ RequestMapping("/process")
public class InvoiceController {

	@Autowired 
	BusinessService businessServiceInstance;
	
	@RequestMapping(value = "/av2/{r_object_id}", method = RequestMethod.GET)
	public Invoice processAV2(@PathVariable String r_object_id){
		Invoice pInvoice = new Invoice();
		pInvoice.setrObjectId(r_object_id);
		return businessServiceInstance.processAV2(pInvoice);
	}
	
	@RequestMapping(value = "/retrieveFromSAP", method = RequestMethod.GET)
	public void retrieveDataFromSAP(Invoice pInvoice){
		businessServiceInstance.retrievingSapInformations(pInvoice);
	}
	
	@RequestMapping(value="getDCTMInvoice", method = RequestMethod.GET)
	public @ResponseBody Invoice retrieveDataFromDCTM(@PathVariable String r_object_id) {
		//checkCredentials();
		Invoice pInvoice = new Invoice();
		pInvoice.setrObjectId(r_object_id);
		pInvoice = businessServiceInstance.processInvoice(pInvoice);
 
		return pInvoice;
 
	}
}
