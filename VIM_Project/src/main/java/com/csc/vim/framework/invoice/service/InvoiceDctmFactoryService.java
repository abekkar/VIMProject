package com.csc.vim.framework.invoice.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csc.vim.framework.bankinformation.model.BankingInformation;
import com.csc.vim.framework.bankinformation.service.BankingInformationService;
import com.csc.vim.framework.common.model.JipSession;
import com.csc.vim.framework.invoice.dao.InvoiceDodcumentumDao;
import com.csc.vim.framework.invoice.model.Invoice;
import com.csc.vim.framework.model.PurchaseOrder;
import com.csc.vim.framework.po.service.PurchaseOrderDctmService;
import com.csc.vim.framework.properties.DctmModelType;
import com.csc.vim.framework.supplier.model.Supplier;
import com.csc.vim.framework.supplier.service.SupplierDctmService;
import com.csc.vim.framework.util.DctmHelper;
import com.documentum.fc.client.DfAuthenticationException;
import com.documentum.fc.client.DfIdentityException;
import com.documentum.fc.client.DfPrincipalException;
import com.documentum.fc.client.DfServiceException;
import com.documentum.fc.common.DfException;

@Service
public class InvoiceDctmFactoryService {

	@Autowired
	private InvoiceDodcumentumDao invoiceDao;
	
	@Autowired
	private InvoiceDctmService invoiceService;
	
	@Autowired
	private PurchaseOrderDctmService poService;
	
	@Autowired
	private SupplierDctmService supplierService;
	
	@Autowired
	private BankingInformationService bankingInfoSrervice;
	
	@Autowired
	private DctmHelper dctmHelper;
	
	public Invoice buildFromCaptiva(String invoice_currency,				// CPT
			String invoiceDate,						// CPT  + !!!
			String freightCosts,					// CPT + !!!!
			String invoiceGrossAmount,				// CPT
			String invoice_iban,					// CPT
			String lifeCycle,
			String invoiceNetAmount,				// CPT
			String packagingCosts,					// CPT
			String invoiceReference,				// CPT
			String invoice_status,
			String invoiceType,
			String invoiceVatAmount,				// CPT
			String salesOrderNumber,				// CPT
			String scanningDate,					// CPT
			String scanningReference,				// CPT
			String supplier_tax_code,				// CPT
			String supplier_vat_code,				// CPT
			String purchase_order_number,			// CPT
			String purchase_order_number_position,	// CPT) {
			List<MultipartFile> pFiles) {
		
		// *** Value by default ***
		if (lifeCycle == null) {
			lifeCycle = "AV1";
		}
		
		if ((invoice_status == null) || (invoice_status.equals("")))
			invoice_status = "0";

		// *** Build the Invoice object ***
		Invoice invoice = new Invoice();
		if (invoiceDate != null)
			invoice.setInvoiceDate(invoiceDate);
		if (freightCosts != null)
			invoice.setFreightCosts(Double.parseDouble(freightCosts));
		invoice.setInvoiceGrossAmount(invoiceGrossAmount);
		invoice.setLifeCycle(lifeCycle);
		invoice.setInvoiceNetAmount(invoiceNetAmount);
		if (packagingCosts != null) invoice.setPackagingCosts(Double.parseDouble(packagingCosts));
		invoice.setInvoiceReference(invoiceReference);
		if (invoice_status != null) invoice.setInvoiceStatus(Integer.parseInt(invoice_status));
		invoice.setInvoiceType(invoiceType);
		invoice.setInvoiceVatAmount(invoiceVatAmount);
		invoice.setSalesOrderNumber(salesOrderNumber);
		invoice.setScanningDate(scanningDate);
		invoice.setScanningReference(scanningReference);
		
		// By default, the invoice is created in a folder with the same name as his scanning reference
		// TODO create the DCTM Folder with scanning_reference
		invoice.setDctmFolder(DctmModelType.WORKING_FOLDER);
		
		return invoice;
	}
	
	
	
	/**
	 * Create into Dctm an Invoice with all his linked object such as PO, Supplier, etc...
	 * 
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pInvoice		Java Invoice to create in DCTM
	 * @param pPo			The Purchase Order to create and link to the invoice
	 * @param pSupplier		The Supplier object to create and link to the invoice
	 * @return The invoice in input updated with a attached po, supplier, r_object_id, ...
	 */
	public Invoice createFullInvoice(Invoice pInvoice, PurchaseOrder pPo, Supplier pSupplier, Collection<BankingInformation> pBankInfo) {
		try {
			if (pInvoice != null) {
				JipSession jipSession = invoiceDao.openSession();
				
				// Create the invoice
				Invoice inv = invoiceService.create(pInvoice, false);
				
				if (inv != null) {
					if (pPo != null) {
						// Create the purchase order and link it to the invoice
						poService.create(pPo, false);
						invoiceService.linkPurchaseOrder(pInvoice, pPo);
					}
					
					if (pSupplier != null) {
						// Create the Supplier and link it to the invoice
						supplierService.create(pSupplier, false);
						invoiceService.linkSupplier(pInvoice, pSupplier);	
					}
					
					if (pBankInfo != null) {
						// Create Banking Information object
						Iterator<BankingInformation> it = pBankInfo.iterator();
						while (it.hasNext()) {
							BankingInformation bankInfo = it.next();
							bankingInfoSrervice.create(bankInfo, false);
						}
						// Link all the created BankingInformation object to the invoice
						invoiceService.linkBankingInformation(pInvoice, pBankInfo);
					}
				}
			}	
		} catch (DfIdentityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DfAuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DfPrincipalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DfServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				invoiceDao.closeSession();
			} catch (DfException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return pInvoice;
	}
	
	/**
	 * Rertrieve from Dctm a Structured Invoice (Invoice with all his po, supplier, iban ,etc...)
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pId	r_object_id of the invoice
	 * @return The structured invoice retrieved from Dctm
	 */
	public Invoice buildById(String pId) {
		Invoice invoice = new Invoice ();
		
		try {
			invoiceDao.openSession();
			
			// 1 - Get the invoice itself
			invoice = invoiceService.getById(pId, true);
			
			// 2 - Get the Purchase Order linked with this invoie
			Collection<PurchaseOrder> poList = poService.getChildrenOfInvoice(pId, true);
			// Note that Only PO is handle
			PurchaseOrder po = poList.iterator().next();
			invoice.setPurchaseOrder(po);

			// 3 - Get the supplier
			Collection<Supplier> supplierList = supplierService.getChildrenOfInvoice(pId, true);
			Supplier supplier = supplierList.iterator().next();
			// Only One supplier by Invoice
			invoice.setSupplierDetail(supplier);
			
			// 4 - Get the banking Info
			Collection<BankingInformation> bankingInfoList = bankingInfoSrervice.getChildrenOfInvoice(pId, true);
			// Only One supplier by Invoice
			invoice.setBankingInformation(bankingInfoList);
			
		} catch (DfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			try {
				invoiceDao.closeSession();
			} catch (DfException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return invoice;	
	}
}
