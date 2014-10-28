package com.csc.vim.framework.supplier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csc.vim.framework.supplier.model.Supplier;
import com.csc.vim.framework.supplier.service.SupplierDctmService;
import com.documentum.fc.common.DfException;

@Controller
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	private SupplierDctmService supplierDctmService;
	
	/**
	 * SSUP23 - Service to Create a supplier in DCTM
	 * 
	 * @since 1.0
	 * @author syongwaiman2
	 * 
	 * @param rObjectId
	 * @param number
	 * @param industry
	 * @param defaultIban
	 * @param address
	 * @param postCode
	 * @param city
	 * @param country
	 * @param email
	 * @param cpd
	 * @param taxNumber
	 * @param name
	 * @param vatNumber
	 * 
	 * @return r_object_id of the supplier
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String create(@RequestParam(required=false) String r_object_id, 
			@RequestParam(required=false) String number,
			@RequestParam(required=false) String industry, 
			@RequestParam(required=false) String default_iban,
			@RequestParam(required=false) String address, 
			@RequestParam(required=false) String post_code,
			@RequestParam(required=false) String city, 
			@RequestParam(required=false) String country,
			@RequestParam(required=false) String email, 
			@RequestParam(required=false) Boolean cpd,
			@RequestParam(required=false) String tax_number, 
			@RequestParam(required=false) String name,
			@RequestParam(required=false) String vat_number) {
		
		Supplier supplier = new Supplier();
		supplier.setrObjectId(r_object_id);
		supplier.setAddress(address);
		supplier.setCity(city);
		supplier.setCountry(country);
		supplier.setCpd(cpd);
		supplier.setDefaultIban(default_iban);
		supplier.setEmail(email);
		supplier.setIndustry(industry);
		supplier.setNumber(number);
		supplier.setPostCode(post_code);
		supplier.setTaxNumber(tax_number);
		supplier.setVatNumber(vat_number);
		supplier.setName(name);
		
		try {
			supplierDctmService.create(supplier, false);
		} catch (DfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return supplier.getrObjectId();
		
	}
}
