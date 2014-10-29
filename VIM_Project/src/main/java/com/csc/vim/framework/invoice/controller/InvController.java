package com.csc.vim.framework.invoice.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.csc.vim.framework.bankinformation.model.BankingInformation;
import com.csc.vim.framework.invoice.model.Invoice;
import com.csc.vim.framework.invoice.service.InvoiceDctmFactoryService;
import com.csc.vim.framework.invoice.service.InvoiceDocumentumService;
import com.csc.vim.framework.model.PurchaseOrder;
import com.csc.vim.framework.supplier.model.Supplier;
import com.csc.vim.framework.util.FileUtils;

@ RequestMapping("/invoice")
@Controller
public class InvController {

	@Autowired
	private InvoiceDocumentumService invoiceService;
	
	@Autowired
	private InvoiceDctmFactoryService invoiceFactory;
	
	@Autowired
	private FileUtils fileUtils;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Invoice getInvoice(@RequestParam String pId) {
		Invoice invoice = invoiceFactory.buildById(pId);
		return invoice;
	}
			
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Integer importInvoice(
			@RequestParam(required = false ) String blocking_code_sap,
			@RequestParam(required = false ) Boolean blocking_code_t,
			@RequestParam(required = false ) Boolean blocking_code_v,
			@RequestParam(required = false ) String company_code,
				@RequestParam(required = false ) String company_tax_number,	
				@RequestParam(required = false ) String company_vat_number,	
			@RequestParam(required = false ) String good_receipts,
			@RequestParam(required = false ) String invoice_category,
			@RequestParam(required = false ) String invoice_country_origin,
				@RequestParam(required = false ) String invoice_currency,				// CPT
				@RequestParam(required = false ) String invoice_date,					// CPT  + !!!
			@RequestParam(required = false ) String invoice_family,
				@RequestParam(required = false ) String freight_cost,					// CPT + !!!!
				@RequestParam(required = false ) String invoice_gross_amount,			// CPT
				@RequestParam(required = false ) String invoice_iban,					// CPT
			@RequestParam(required = false ) String invoice_life_cycle,
				@RequestParam(required = false ) String invoice_net_amount,				// CPT
				@RequestParam(required = false ) String packaging_cost,					// CPT
				@RequestParam(required = false ) String invoice_reference,				// CPT
			@RequestParam(required = false ) String invoice_status,
			@RequestParam(required = false )String invoice_type,
				@RequestParam(required = false ) String invoice_vat_amount,				// CPT
				@RequestParam(required = false )String sales_order_number,				// CPT
			@RequestParam(required = false )String sales_order_position,
				@RequestParam(required = false ) String scanning_date,					// CPT
				@RequestParam(required = false ) String scanning_reference,				// CPT
			
				@RequestParam(required = false ) String supplier_tax_code,				// CPT
				@RequestParam(required = false ) String supplier_vat_code,				// CPT
			
				@RequestParam(required = false ) String purchase_order_number,			// CPT
				@RequestParam(required = false ) String purchase_order_number_position,	// CPT
							
			@RequestParam(value="file") List<MultipartFile> pFiles
			) {
		//List<MultipartFile> pFiles = null;
		
		for (MultipartFile file: pFiles) {
			try {				
				System.out.println("transform the HTTP File into a java.io file");
				// transform the HTTP File into a java.io file
				File javafile = fileUtils.multipartToFile(file);
				
				Invoice invoice = invoiceFactory.buildFromCaptiva(invoice_currency, 
						invoice_date, 
						freight_cost, 
						invoice_gross_amount,
						invoice_iban, 
						invoice_life_cycle, 
						invoice_net_amount, 
						packaging_cost, 
						invoice_reference, 
						invoice_status, 
						invoice_type, 
						invoice_vat_amount, 
						sales_order_number, 
						scanning_date, 
						scanning_reference, 
						supplier_tax_code, 
						supplier_vat_code, 
						purchase_order_number, 
						purchase_order_number_position, pFiles);
				invoice.setContent(javafile);
				
				// Build the Purchase Order object
				PurchaseOrder po = null;
				if (purchase_order_number != null) {
					po = new PurchaseOrder();
					po.setPoNumber(purchase_order_number);
					po.setPoNumberPosition(purchase_order_number_position);
				}
				
				// Build the Supplier object
				Supplier supplier = null;
				if ((supplier_vat_code != null) || (supplier_tax_code != null)) {
					supplier = new Supplier();
					supplier.setTaxNumber(supplier_tax_code);
					supplier.setVatNumber(supplier_vat_code);
				}
			
				// Build the Bankinginfo object
				Collection<BankingInformation> bankInfos = new ArrayList<BankingInformation>();
				if (invoice_iban != null) {
					BankingInformation bankInfo = new BankingInformation();
					bankInfo.setIban(invoice_iban);
					
					bankInfos.add(bankInfo);
				}
				
				
				System.out.println("Create invoice");
				invoiceFactory.createFullInvoice(invoice, po, supplier, bankInfos);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}		
		return 0;
	}
	
	
	
}
