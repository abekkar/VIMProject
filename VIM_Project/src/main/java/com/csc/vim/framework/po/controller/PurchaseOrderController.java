package com.csc.vim.framework.po.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csc.vim.framework.model.PurchaseOrder;
import com.csc.vim.framework.po.service.PurchaseOrderDctmService;
import com.csc.vim.framework.util.DateUtils;
import com.documentum.fc.common.DfException;

@Controller
@RequestMapping("/po")
public class PurchaseOrderController {

	@Autowired
	DateUtils dateUtils;
	
	@Autowired
	PurchaseOrderDctmService poService;
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String create(@RequestParam String supplierNumber,
						@RequestParam String poDocumentType,
						@RequestParam String supplierName,
						@RequestParam String poDate,
						@RequestParam String poNumber,
						@RequestParam String poNumberPosition,
						@RequestParam(required=false) String invoice_id) {
		PurchaseOrder po = new PurchaseOrder();
		po.setSupplierNumber(supplierNumber);
		po.setPoDocumentType(poDocumentType);
		po.setSupplierName(supplierName);
		po.setPoDate(dateUtils.stringToDateDctm(poDate, "dd-MM-YYYY"));
		po.setPoNumber(poNumberPosition);
		po.setPoNumberPosition(poNumberPosition);
		po.setParentId(invoice_id);
		try {
			poService.create(po, false);
			
			return po.getrObjectId();
		} catch (DfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
