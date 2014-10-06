package com.csc.vim.framework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.service.BusinessService;

@Controller
@ RequestMapping("/process")
public class InvoiceController {

	@Autowired 
	BusinessService businessServiceInstance;
	
	@RequestMapping(value = "/av2", method = RequestMethod.POST)
	public void processAV2(Invoice pInvoice){
		businessServiceInstance.processAV2(pInvoice);
	}
	
	@RequestMapping(value = "/retrieveFromSAP", method = RequestMethod.POST)
	public void retrieveDataFromSAP(Invoice pInvoice){
		businessServiceInstance.retrievingSapInformations(pInvoice);
	}
}
