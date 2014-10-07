package com.csc.vim.framework.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.csc.vim.framework.dao.IInvoiceDctmDao;
import com.csc.vim.framework.model.BankDetails;
import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.model.InvoiceLine;
import com.csc.vim.framework.model.Message;
import com.csc.vim.framework.model.PurchaseOrder;
import com.csc.vim.framework.model.Supplier;
import com.csc.vim.framework.util.DCTMHelper;
import com.csc.vim.framework.util.DateUtils;
import com.csc.vim.framework.util.SapHelper;
import com.documentum.fc.client.DfAuthenticationException;
import com.documentum.fc.client.DfIdentityException;
import com.documentum.fc.client.DfPrincipalException;
import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.DfServiceException;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.sap.conn.jco.JCoException;

@Component
public class InvoiceDctmDao implements IInvoiceDctmDao {

	@Autowired
	private DCTMHelper dctmInstance;
	@Autowired
	private DateUtils dateUtils;
	@Autowired
	private SapHelper sapInstance;

	protected final Logger logger = LoggerFactory
			.getLogger(InvoiceDctmDao.class);
	
	private static String NAMESPACE = "demat";
	private static String DQL_QUERY_RETRIEVE_INVOICE_LINE = "select * from "+NAMESPACE+"_invoice_line,"+NAMESPACE+"_invoice where "+NAMESPACE+"_invoice.r_object_id='";
	private static String DQL_QUERY_RETRIEVE_INVOICE_SUPPLIER = "select * from "+NAMESPACE+"_supplier,"+NAMESPACE+"_invoice where "+NAMESPACE+"_invoice.r_object_id='";
	private static String DQL_QUERY_RETRIEVE_INVOICE_PO = "select * from "+NAMESPACE+"_purchase_order,"+NAMESPACE+"_invoice where "+NAMESPACE+"_invoice.r_object_id='";
	private static String DQL_QUERY_RETRIEVE_BANK_DETAIL = "select * from "+NAMESPACE+"_invoice_bankinforma,"+NAMESPACE+"_invoice where "+NAMESPACE+"_invoice.r_object_id='";
	private static String DQL_QUERY_RETRIEVE_APPROVER = "select * from message,"+NAMESPACE+"_invoice where message.message_code='DCTM_AVWA' and "+NAMESPACE+"_invoice.r_object_id='";
	private static String DQL_QUERY_RETRIEVE_PROCESSOR = "select * from message,"+NAMESPACE+"_invoice where  "+NAMESPACE+"_invoice.r_object_id='";
	private static String DQL_QUERY_RETRIEVE_INVOICE_BY_STATUS = "select * from "+NAMESPACE+"_invoice where invoice_status='";

	/*
	 *  Invoice Properties
	 */
	private static String INVOICE_TYPE            = "invoice_type";
	private static String INVOICE_CATEGORY        = "invoice_category";
	private static String FIRST_LEVEL_CONTROLLER  = "first_level_controller";
	private static String GLOBAL_LEVEL_CONTROLLER = "global_level_controller"; 
	private static String SELECTED_APPROVAL_GROUP = "selected_approval_group";
	private static String INVOICE_CURRENCY        = "invoice_currency";
	private static String INVOICE_DATE            = "invoice_date";
	private static String COMPANY_CODE            = "company_code";
	private static String INVOICE_FAMILY          = "invoice_family";
	private static String INVOICE_GROSS_AMOUNT    = "invoice_gross_amount";
	private static String INVOICE_NET_AMOUNT      = "invoice_net_amount";
	private static String INVOICE_NET_AMOUNT_EUR  = "invoice_net_amount_eur";
	private static String INVOICE_VAT_AMOUNT      = "invoice_vat_amount";
	private static String INVOICE_REFERENCE       = "invoice_reference";
	private static String INVOICE_STATUS          = "invoice_status";
	private static String BLOCKING_CODE_SAP       = "blocking_code_sap";
	private static String BLOCKING_CODE_T         = "blocking_code_t";
	private static String BLOCKING_CODE_V         = "blocking_code_v";
	private static String INVOICE_LCT             = "invoice_lct";
	private static String INVOICE_UCT             = "invoice_uct";
	private static String INVOICE_COUNTRY_ORIGIN  = "invoice_country_origin";
	private static String SAP_FI_DOC_NUM          = "sap_fi_document_number";
	private static String SAP_MM_DOC_NUM          = "sap_mm_document_number";
	private static String SAP_FI_DOC_DATE         = "sap_fi_document_date";
	private static String SAP_MM_DOC_DATE         = "sap_mm_document_date";
	private static String FREIGHT_COST            = "freight_cost";
	private static String PACKAGING_COST          = "packaging_cost";
	private static String PAYMENT_CONDITION       = "payment_condition";
	private static String SEL_THRESHOLD_AMOUNT    = "selected_threshold_amount";
	private static String SELECTED_IBAN           = "selected_iban";
	private static String SCANNING_REFERENCE      = "scanning_reference";
	private static String SCANNING_DATE           = "scanning_date";
	private static String COMPANY_TAX_NUMBER      = "company_tax_number";
	private static String COMPANU_VAT_NUMBER      = "company_vat_number";
	private static String SAP_INVOICE_CREATOR     = "sap_invoice_creator";
	private static String SALES_ORDER_NUMBER      = "sales_order_number";
	private static String SALES_ORDER_POSITION    = "sales_order_position";
	private static String GOOD_RECEIPTS           = "good_receipts";
	
	/*
	 * Invoice Line Properties
	 */
	private static String R_OBJECT_ID             = "r_object_id";
	private static String VENDOR_ASSIGNMENT_NUMBER= "vendor_assignment_number";
	private static String COST_CENTER             = "cost_center";
	private static String GL_ITEM_TEXT            = "gl_item_text";
	private static String GL_ACCOUNT              = "gl_account";
	private static String GL_ACCOUNT_NUMBER       = "gl_account_number";
	private static String INTERNAL_ORDER          = "internal_order";
	private static String PARTNER_PROFIT_CENTER   = "partner_profit_center";
	private static String PLANT_NUMBER            = "plant_number";
	private static String PROFIT_CENTER           = "profit_center";
	private static String QUANTITY                = "quantity";
	private static String SALES_ORDER             = "sales_order";
	private static String ISALES_ORDER_POSITION   = "sales_order_position";
	private static String VENDOR_ITEM_NUMBER      = "vendor_item_number";
	private static String VENDOR_ITEM_TEXT        = "vendor_item_text";
	private static String WBS                     = "wbs_element";
	private static String MATERIAL                = "material";
	private static String SALES_TAX_ORDER         = "sales_tax_order";
	private static String GL_SALES_TAX_CODE       = "gl_sales_tax_code";
	private static String TAX_RATE                = "tax_rate";
	private static String BASE_LINE_DATE          = "base_line_date";
	private static String TAX_ITEM_NUMBER         = "tax_item_number";
	private static String TAX_GL_ACCOUNT          = "tax_gl_account";
	private static String GL_ASSIGNMENT_NUMBER    = "gl_assignment";
	private static String QUANTITY_UNIT           = "quantity_unit";
	
	/*
	 * Supplier Properties
	 */
	private static String SUPPLIER_CPD            = "cpd";
	private static String SUPPLIER_INDUSTRY       = "industry";
	private static String SUPPLIER_ADRESS         = "adress";
	private static String SUPPLIER_CITY           = "city";
	private static String SUPPLIER_COUNTRY        = "country";
	private static String SUPPLIER_EMAIL          = "email";
	private static String SUPPLIER_POST_CODE      = "post_code";
	private static String SUPPLIER_NAME           = "name";
	private static String SUPPLIER_NUMBER         = "number";
	private static String SUPPLIER_TAX_NUMBER     = "tax_number";
	private static String SUPPLIER_VAT_NUMBER     = "vat_number";
	private static String SUPPLIER_DEFAULT_IBAN   = "default_iban";
	
	/*
	 * Bank Informations Properties 
	 */
	private static String ACCOUNT_NAME            = "account_name";
	private static String BANK_NAME               = "bank_name";
	private static String BANK_IBAN               = "iban";
	
	/*
	 * Purchase Order Properties
	 */
	private static String PURCHASE_ORDER_DATE      = "po_date";
	private static String PURCHASE_ORDER_DOC_DATE  = "po_document_type";
	private static String PURCHASE_ORDER_NUMBER    = "po_number";
	private static String PURCHASE_ORDER_NUMBER_POS= "po_number_position";
	private static String PO_SUPPLIER_NAME         = "supplier_name";
	private static String PO_SUPPLIER_NUMBER       = "supplier_number";
	
	/*
	 * Approvers Properties
	 */
	private static String APPROVER_LOGIN           = "user_login";
	private static String APPROVER_TEXT            = "message_text";
	private static String APPROVER_ROLE            = "role";
	private static String APPROVER_OBJECT_ID       = "r_object_id";
	
	/**
	 * See the IInvoiceDao Interface to get informations about every method
	 * 
	 * @author abekkar
	 * @since 1.0
	 */
	public Invoice read(Invoice pInvoice) throws JCoException, IOException {
		try {
			IDfSession session = dctmInstance.getSession();
			pInvoice = populateInvoiceProperties(pInvoice, session);
			pInvoice = populateInvoiceLines(pInvoice, session);
			pInvoice = populateSupplierDetail(pInvoice, session);
			pInvoice = populateBankInformations(pInvoice,  session);
			pInvoice = populatePurchaseOrder(pInvoice,  session);
			pInvoice = populateApprovers(pInvoice, session);
			pInvoice = populateprocessors(pInvoice, session);
			dctmInstance.closeSession(session);
		} catch (DfIdentityException e) {
			logger.error(e.getMessage());
		} catch (DfAuthenticationException e) {
			logger.error(e.getMessage());
		} catch (DfPrincipalException e) {
			logger.error(e.getMessage());
		} catch (DfServiceException e) {
			logger.error(e.getMessage());
		} catch (DfException e) {
			logger.error(e.getMessage());
		}
		return pInvoice;
	}

	public Invoice update(Invoice pInvoice) throws Exception {

		IDfQuery DQLquery = new DfQuery();
		IDfSession session = dctmInstance.getSession();
		DQLquery = updateInvoiceProperties( pInvoice, DQLquery);
		DQLquery.execute(dctmInstance.getSession(), IDfQuery.DF_EXEC_QUERY);		 
		DQLquery = updateSupplierDetail(pInvoice, DQLquery, session);
		DQLquery.execute(dctmInstance.getSession(), IDfQuery.DF_EXEC_QUERY);
		DQLquery = updatePurchaseOrder(pInvoice, DQLquery, session);
		DQLquery.execute(dctmInstance.getSession(),IDfQuery.DF_EXEC_QUERY);
		DQLquery = updateBankInformations(pInvoice, DQLquery, session);
		DQLquery.execute(dctmInstance.getSession(),IDfQuery.DF_EXEC_QUERY);
		return pInvoice;
	}

	public List<Invoice> getInvoicesByStatut(Integer status) throws DfIdentityException, DfAuthenticationException,DfPrincipalException, DfServiceException, DfException,JCoException, IOException {
		IDfQuery queryGetInvoiceByStatus = new DfQuery();
		IDfQuery DQLquery = new DfQuery();
		List<Invoice> listOfInvoices = new ArrayList<Invoice>();
		queryGetInvoiceByStatus.setDQL(DQL_QUERY_RETRIEVE_INVOICE_BY_STATUS	+ status + "'");
		IDfCollection invoiceCol = queryGetInvoiceByStatus.execute(dctmInstance.getSession(), IDfQuery.DF_READ_QUERY);
		if (null != invoiceCol) 
		{
			for (int i = 0; i < invoiceCol.getAttrCount(); i++) {
				Invoice invoiceInstance = new Invoice();
				invoiceInstance.setrObjectId(invoiceCol.getString(R_OBJECT_ID));
				listOfInvoices.add(read(invoiceInstance));
			}
		}
		logger.debug("Retrieving Invoices by status: "+ queryGetInvoiceByStatus.toString());
		DQLquery.setDQL(queryGetInvoiceByStatus.toString());
		DQLquery.execute(dctmInstance.getSession(), IDfQuery.DF_EXEC_QUERY);
		return listOfInvoices;
	}

	public Invoice retrieveInvoice(Invoice pInvoice) throws Exception {
		return read(pInvoice);
	}
	
	
	private Invoice populateInvoiceProperties(Invoice pInvoice,IDfSession session){

		IDfSysObject invoice = null;
		try {
			invoice = (IDfSysObject) session.getObject(new DfId(pInvoice.getrObjectId()));
			// Getting invoice properties
			pInvoice.setInvoiceType(invoice.getString(INVOICE_TYPE));
			if (null != invoice.getString(INVOICE_CATEGORY) && "" != invoice.getString(INVOICE_CATEGORY))
				pInvoice.setInvoiceCategory(Integer.parseInt(invoice.getString(INVOICE_CATEGORY)));
			pInvoice.setFirstLevelController(invoice.getString(FIRST_LEVEL_CONTROLLER));
			pInvoice.setGlobalLevelController(invoice.getString(GLOBAL_LEVEL_CONTROLLER));
			pInvoice.setSelectedApprovalGroup(invoice.getString(SELECTED_APPROVAL_GROUP));
			pInvoice.setInvoiceCurrency(invoice.getString(INVOICE_CURRENCY));
			pInvoice.setInvoiceDate(invoice.getString(INVOICE_DATE));
			pInvoice.setCompanyCode(invoice.getString(COMPANY_CODE));
			if (null != invoice.getString(INVOICE_FAMILY))
				pInvoice.setInvoiceFamily(Integer.parseInt(invoice.getString(INVOICE_FAMILY)));
			pInvoice.setInvoiceGrossAmount(invoice.getString(INVOICE_GROSS_AMOUNT));
			pInvoice.setInvoiceNetAmount(invoice.getString(INVOICE_NET_AMOUNT));
			pInvoice.setInvoiceNetAmountEur(Double.parseDouble(invoice.getString(INVOICE_NET_AMOUNT_EUR)));
			pInvoice.setInvoiceVatAmount(invoice.getString(INVOICE_VAT_AMOUNT));
			pInvoice.setInvoiceReference(invoice.getString(INVOICE_REFERENCE));
			if (null != invoice.getString(INVOICE_STATUS))
				pInvoice.setInvoiceStatus(Integer.parseInt(invoice.getString(INVOICE_STATUS)));
			pInvoice.setSapBlockingCode(invoice.getString(BLOCKING_CODE_SAP));
			pInvoice.setBlockingCodeT(invoice.getString(BLOCKING_CODE_T));
			pInvoice.setBlockingCodeV(invoice.getString(BLOCKING_CODE_V));
			pInvoice.setInvoiceLCT(Double.parseDouble(invoice.getString(INVOICE_LCT)));
			pInvoice.setInvoiceUCT(Double.parseDouble(invoice.getString(INVOICE_UCT)));
			pInvoice.setInvoicecountryOrigin(invoice.getString(INVOICE_COUNTRY_ORIGIN));
			pInvoice.setSapFIDocumentNumber(invoice.getString(SAP_FI_DOC_NUM));
			pInvoice.setSapMMDocumentNumber(invoice.getString(SAP_MM_DOC_NUM));
			if (null != invoice.getString(SAP_FI_DOC_DATE))
				pInvoice.setSapFIDocumentDate(Integer.parseInt(invoice.getString(SAP_FI_DOC_DATE)));
			if (null != invoice.getString(SAP_MM_DOC_DATE))
				pInvoice.setSapMMDocumentDate(Integer.parseInt(invoice.getString(SAP_MM_DOC_DATE)));
			pInvoice.setFreightCosts(Double.parseDouble(invoice.getString(FREIGHT_COST)));
			pInvoice.setPackagingCosts(Double.parseDouble(invoice.getString(PACKAGING_COST)));
			pInvoice.setPaymentCondition(invoice.getString(PAYMENT_CONDITION));
			pInvoice.setSelectedThresholdAmount(invoice.getString(SEL_THRESHOLD_AMOUNT));
			pInvoice.setSelectedIban(invoice.getString(SELECTED_IBAN));
			pInvoice.setScanningReference(invoice.getString(SCANNING_REFERENCE));
			pInvoice.setScanningDate(invoice.getString(SCANNING_DATE));
			pInvoice.setCompanyTaxNumber(invoice.getString(COMPANY_TAX_NUMBER));
			pInvoice.setCompanyVatNumber(invoice.getString(COMPANU_VAT_NUMBER));
			pInvoice.setSapInvoiceCreator(invoice.getString(SAP_INVOICE_CREATOR));
			pInvoice.setSalesOrderNumber(invoice.getString(SALES_ORDER_NUMBER));
			pInvoice.setSalesOrderPosition(invoice.getString(SALES_ORDER_POSITION));
	
			StringTokenizer listOfGoodReceipts = new StringTokenizer(invoice.getAllRepeatingStrings(GOOD_RECEIPTS, ","), ",");
			if (null != listOfGoodReceipts) {
				pInvoice.setGoodReceiptNumber(new ArrayList<String>());
				while (listOfGoodReceipts.hasMoreTokens())
					while (listOfGoodReceipts.hasMoreTokens()) {
						pInvoice.getGoodReceiptNumber().add(
								listOfGoodReceipts.nextToken());
					}
			}
		} catch (DfException e) {
			logger.error("Error during populating Invoice Properties From DCTM "+ e.getMessage());
		}
		return pInvoice;
	}
	
	private Invoice populateInvoiceLines(Invoice pInvoice,IDfSession session){
		// Populate invoiceLines
		try {
			pInvoice.setInvoiceLines(new ArrayList<InvoiceLine>());
			IDfQuery queryGetInvoiceLine = new DfQuery();
			queryGetInvoiceLine.setDQL(DQL_QUERY_RETRIEVE_INVOICE_LINE+ pInvoice.getrObjectId() + "'");
			IDfCollection invoiceLineCol = queryGetInvoiceLine.execute(session,IDfQuery.DF_READ_QUERY);
			if (null != invoiceLineCol)
			{
				pInvoice.setInvoiceLines(new ArrayList<InvoiceLine>());
				while (invoiceLineCol.next()) 
				{
					InvoiceLine invoiceLine = new InvoiceLine();
					invoiceLine.setrObjectId(invoiceLineCol.getString(R_OBJECT_ID));
					invoiceLine.setVendorAssignmentNumber(invoiceLineCol.getString(VENDOR_ASSIGNMENT_NUMBER));
					invoiceLine.setCostCenter(invoiceLineCol.getString(COST_CENTER));
					invoiceLine.setGlItemText(invoiceLineCol.getString(GL_ITEM_TEXT));
					invoiceLine.setGlAccount(invoiceLineCol.getString(GL_ACCOUNT));
					invoiceLine.setGlAccountNumber(invoiceLineCol.getString(GL_ACCOUNT_NUMBER));
					invoiceLine.setInternalOrder(invoiceLineCol.getString(INTERNAL_ORDER));
					invoiceLine.setPartnerProfitCenter(invoiceLineCol.getString(PARTNER_PROFIT_CENTER));
					invoiceLine.setPlantNumber(invoiceLineCol.getString(PLANT_NUMBER));
					invoiceLine.setProfitCenter(invoiceLineCol.getString(PROFIT_CENTER));
					invoiceLine.setQuantity(Integer.parseInt(invoiceLineCol.getString(QUANTITY)));
					invoiceLine.setSalesOrder(invoiceLineCol.getString(SALES_ORDER));
					invoiceLine.setVendorItemNumber(invoiceLineCol.getString(VENDOR_ITEM_NUMBER));
					invoiceLine.setWbs(invoiceLineCol.getString(WBS));
					invoiceLine.setMaterial(invoiceLineCol.getString(MATERIAL));
					invoiceLine.setSalestaxCode(invoiceLineCol.getString(SALES_TAX_ORDER));
					invoiceLine.setSalestaxCode(invoiceLineCol.getString(GL_SALES_TAX_CODE));
					invoiceLine.setSalesOrderPosition(Integer.parseInt(invoiceLineCol.getString(ISALES_ORDER_POSITION)));
					invoiceLine.setVendorItemText(invoiceLineCol.getString(VENDOR_ITEM_TEXT));
					invoiceLine.setGlAssignment(invoiceLineCol.getString(GL_ASSIGNMENT_NUMBER));
					invoiceLine.setQuantityUnit(invoiceLineCol.getString(QUANTITY_UNIT));
					//TODO
//					invoiceLine.setBaseLineDate(dateUtils.stringToDate(invoiceLineCol.getString(BASE_LINE_DATE),"dd/mm/yyyy"));
//					invoiceLine.setTaxRate(Integer.parseInt(invoiceLineCol.getString(TAX_RATE)));
//					invoiceLine.setTaxItemNumber(Integer.parseInt(invoiceLineCol.getString(TAX_ITEM_NUMBER)));
//					invoiceLine.setGlTaxAccount(invoiceLineCol.getString(TAX_GL_ACCOUNT));
					pInvoice.getInvoiceLines().add(invoiceLine);
				}
			}
		} catch (DfException e) {
			logger.error("Error during populating Invoice Lines Properties From DCTM "+ e.getMessage());
		}
		return pInvoice;
	}
	
	private Invoice populateSupplierDetail(Invoice pInvoice,IDfSession session){
		// Populate supplier detail
		try{
			IDfQuery queryGetInvoiceSupplier = new DfQuery();
			queryGetInvoiceSupplier.setDQL(DQL_QUERY_RETRIEVE_INVOICE_SUPPLIER+ pInvoice.getrObjectId() + "'");
			IDfCollection invoiceSupplierDetail = queryGetInvoiceSupplier.execute(session, IDfQuery.DF_READ_QUERY);
			if (null != invoiceSupplierDetail) {
				while (invoiceSupplierDetail.next()) 
				{
					Supplier supplier = new Supplier();
					supplier.setSupplierSelectedIban(invoiceSupplierDetail.getString(SUPPLIER_DEFAULT_IBAN));
					if (invoiceSupplierDetail.getString(SUPPLIER_CPD) == "0")
						supplier.setSupplierCPD(false);
					else
						supplier.setSupplierCPD(true);
					supplier.setSupplierIndustry(invoiceSupplierDetail.getString(SUPPLIER_INDUSTRY));
					supplier.setSupplierNumber(invoiceSupplierDetail.getString(SUPPLIER_NUMBER));
					supplier.setSupplierInvoiceAddress(invoiceSupplierDetail.getString(SUPPLIER_ADRESS));
					supplier.setSupplierInvoiceCity(invoiceSupplierDetail.getString(SUPPLIER_CITY));
					supplier.setSupplierInvoiceCountry(invoiceSupplierDetail.getString(SUPPLIER_COUNTRY));
					supplier.setSupplierInvoiceEmail(invoiceSupplierDetail.getString(SUPPLIER_EMAIL));
					supplier.setSupplierInvoicePostCode(invoiceSupplierDetail.getString(SUPPLIER_POST_CODE));
					supplier.setSupplierName(invoiceSupplierDetail.getString(SUPPLIER_NAME));
					supplier.setSupplierTaxNumber(invoiceSupplierDetail.getString(SUPPLIER_TAX_NUMBER));
					supplier.setSupplierVatNumber(invoiceSupplierDetail.getString(SUPPLIER_VAT_NUMBER));
					pInvoice.setSupplierDetail(supplier);
				}
			}
		} catch (DfException e) {
			logger.error("Error during populating Supplier Detail Properties From DCTM "+ e.getMessage());
		}
		return pInvoice;
	}

	private Invoice populateBankInformations(Invoice pInvoice, IDfSession session){
		// Populate Bank Detail
		try{
			IDfQuery queryGetBankDetail = new DfQuery();
			queryGetBankDetail.setDQL(DQL_QUERY_RETRIEVE_BANK_DETAIL+ pInvoice.getrObjectId() + "'");
			IDfCollection invoiceBankDetail = queryGetBankDetail.execute(session, IDfQuery.DF_READ_QUERY);
			if (null != invoiceBankDetail) {
				pInvoice.setListOfBankDetails(new ArrayList<BankDetails>());
				while (invoiceBankDetail.next()) {
					BankDetails bankDetail = new BankDetails();
					bankDetail.setBankName(invoiceBankDetail.getString(BANK_NAME));
					bankDetail.setAccountIban(invoiceBankDetail.getString(BANK_IBAN));
					bankDetail.setAccountName(invoiceBankDetail.getString(ACCOUNT_NAME));
					pInvoice.getListOfBankDetails().add(bankDetail);
				}
			}
		} catch (DfException e) {
			logger.error("Error during populating Supplier Detail Properties From DCTM "+ e.getMessage());
		}
		return pInvoice;	
	}
	
	private Invoice populatePurchaseOrder(Invoice pInvoice, IDfSession session){
		// Populate Bank Detail
		try{
			IDfQuery queryGetPurchaseOrder = new DfQuery();
			queryGetPurchaseOrder.setDQL(DQL_QUERY_RETRIEVE_INVOICE_PO+ pInvoice.getrObjectId() + "'");
			IDfCollection invoicePurchaseOrder = queryGetPurchaseOrder.execute(session, IDfQuery.DF_READ_QUERY);
			if (null != invoicePurchaseOrder) {
				if (null == pInvoice.getPurchaseOrder())
				pInvoice.setPurchaseOrder(new PurchaseOrder());
				while (invoicePurchaseOrder.next()) {
					
					pInvoice.getPurchaseOrder().setPoDocumentType(invoicePurchaseOrder.getString(PURCHASE_ORDER_DOC_DATE));
					pInvoice.getPurchaseOrder().setPoDate(dateUtils.stringToDate(invoicePurchaseOrder.getString(PURCHASE_ORDER_DATE),"dd-mm-yyyy"));
					pInvoice.getPurchaseOrder().setPoNumber(invoicePurchaseOrder.getString(PURCHASE_ORDER_NUMBER));
					pInvoice.getPurchaseOrder().setPoNumberPosition(invoicePurchaseOrder.getString(PURCHASE_ORDER_NUMBER_POS));
					pInvoice.getPurchaseOrder().setSupplierName(invoicePurchaseOrder.getString(PO_SUPPLIER_NAME));
					pInvoice.getPurchaseOrder().setSupplierNumber(invoicePurchaseOrder.getString(PO_SUPPLIER_NUMBER));
				}
			}
		} catch (DfException e) {
			logger.error("Error during populating Purchase Order Properties From DCTM "+ e.getMessage());
		}
		return pInvoice;	
	}
	
	private Invoice populateApprovers(Invoice pInvoice, IDfSession session){
		// Populate Approver list
		try{
			IDfQuery queryGetApprovers = new DfQuery();
			queryGetApprovers.setDQL(DQL_QUERY_RETRIEVE_APPROVER+ pInvoice.getrObjectId() + "'");
			IDfCollection invoiceApproverList = queryGetApprovers.execute(session, IDfQuery.DF_READ_QUERY);
			if (null != invoiceApproverList) {
				pInvoice.setApproverGroupList(new ArrayList<Message>());
				while (invoiceApproverList.next()) {
					Message message = new Message();
					message.setLogin(invoiceApproverList.getString(APPROVER_LOGIN));
					message.setContentText(invoiceApproverList.getString(APPROVER_TEXT));
					message.setrObjectId(invoiceApproverList.getString(APPROVER_OBJECT_ID));
					message.setMessageCode(invoiceApproverList.getString(APPROVER_ROLE));
					pInvoice.getApproverGroupList().add(message);
				}
			}
		} catch (DfException e) {
			logger.error("Error during populating Approvers Properties From DCTM "+ e.getMessage());
		}
		return pInvoice;
	}
	
	
	private Invoice populateprocessors(Invoice pInvoice, IDfSession session){
		// Populate Processor list
		try{
			IDfQuery queryGetProcessors = new DfQuery();
			queryGetProcessors.setDQL(DQL_QUERY_RETRIEVE_PROCESSOR+ pInvoice.getrObjectId() + "'");
			IDfCollection invoiceProcessorList = queryGetProcessors.execute(session, IDfQuery.DF_READ_QUERY);
			if (null != invoiceProcessorList) {
				pInvoice.setProcessorDecision(new ArrayList<Message>());
				while (invoiceProcessorList.next()) {
					Message message = new Message();
					message.setLogin(invoiceProcessorList.getString(APPROVER_LOGIN));
					message.setContentText(invoiceProcessorList.getString(APPROVER_TEXT));
					message.setrObjectId(invoiceProcessorList.getString(APPROVER_OBJECT_ID));
					message.setMessageCode(invoiceProcessorList.getString(APPROVER_ROLE));
					pInvoice.getProcessorDecision().add(message);
				}
			}
		} catch (DfException e) {
			logger.error("Error during populating Processors Properties From DCTM "+ e.getMessage());
		}
		return pInvoice;
	}

	
	private IDfQuery updateInvoiceProperties(Invoice pInvoice,IDfQuery DQLquery){
		StringBuilder invoiceDqlUpdater = new StringBuilder("UPDATE  "+NAMESPACE+"_invoice OBJECTS ");
		// Setting invoice properties
		invoiceDqlUpdater.append("SET "+INVOICE_TYPE+" ="+ pInvoice.getInvoiceType() + " ");
		invoiceDqlUpdater.append("SET "+INVOICE_CATEGORY+" ="+ pInvoice.getInvoiceCategory() + " ");
		invoiceDqlUpdater.append("SET "+FIRST_LEVEL_CONTROLLER+" ='"+ pInvoice.getFirstLevelController() + "' ");
		invoiceDqlUpdater.append("SET "+GLOBAL_LEVEL_CONTROLLER+" ='"+ pInvoice.getGlobalLevelController() + "' ");
		invoiceDqlUpdater.append("SET "+SELECTED_APPROVAL_GROUP+" ='"+ pInvoice.getSelectedApprovalGroup() + "' ");
		invoiceDqlUpdater.append("SET "+INVOICE_CURRENCY+" ='"+ pInvoice.getInvoiceCurrency() + "' ");
		if (!pInvoice.getInvoiceDate().equalsIgnoreCase("nulldate") )
			invoiceDqlUpdater.append("SET "+INVOICE_DATE+" ="+ dateUtils.stringToDate(pInvoice.getInvoiceDate(), "dd-mm-yyyy") + " ");
		invoiceDqlUpdater.append("SET "+COMPANY_CODE+" ='"+ pInvoice.getCompanyCode() + "' ");
		invoiceDqlUpdater.append("SET "+INVOICE_FAMILY+"="+ pInvoice.getInvoiceFamily() + " ");
		invoiceDqlUpdater.append("SET "+INVOICE_GROSS_AMOUNT+" ="+ pInvoice.getInvoiceGrossAmount() + " ");
		invoiceDqlUpdater.append("SET "+INVOICE_NET_AMOUNT+" ="+ pInvoice.getInvoiceNetAmount() + " ");
		invoiceDqlUpdater.append("SET "+INVOICE_NET_AMOUNT_EUR+" ="+ pInvoice.getInvoiceNetAmountEur() + " ");
		invoiceDqlUpdater.append("SET "+INVOICE_VAT_AMOUNT+" ="+ pInvoice.getInvoiceVatAmount() + " ");
		invoiceDqlUpdater.append("SET "+INVOICE_REFERENCE+" ='"+ pInvoice.getInvoiceReference() + "' ");
		invoiceDqlUpdater.append("SET "+INVOICE_STATUS+" ="+ pInvoice.getInvoiceStatus() + " ");
		invoiceDqlUpdater.append("SET "+BLOCKING_CODE_SAP+" ='"+ pInvoice.getSapBlockingCode() + "' ");
		if (pInvoice.getBlockingCodeT()== null || pInvoice.getBlockingCodeT().equalsIgnoreCase("F"))
			invoiceDqlUpdater.append("SET "+BLOCKING_CODE_T+" ="+ false + " ");
		else 
			invoiceDqlUpdater.append("SET "+BLOCKING_CODE_T+" ="+ true + " ");
		if (pInvoice.getBlockingCodeV()== null || pInvoice.getBlockingCodeV().equalsIgnoreCase("F"))
			invoiceDqlUpdater.append("SET "+BLOCKING_CODE_V+" ="+ false + " ");
		else
			invoiceDqlUpdater.append("SET "+ BLOCKING_CODE_V+" ="+ true + " ");
		invoiceDqlUpdater.append("SET "+INVOICE_LCT+" ="+ pInvoice.getInvoiceLCT()+ " ");
		invoiceDqlUpdater.append("SET "+INVOICE_UCT+" ="+ pInvoice.getInvoiceUCT() + " ");
		invoiceDqlUpdater.append("SET "+INVOICE_COUNTRY_ORIGIN+" ='"+ pInvoice.getInvoicecountryOrigin() + "' ");
		invoiceDqlUpdater.append("SET "+SAP_FI_DOC_NUM+" ='"+ pInvoice.getSapFIDocumentNumber() + "' ");
		invoiceDqlUpdater.append("SET "+SAP_MM_DOC_NUM+" ='"+ pInvoice.getSapMMDocumentNumber() + "' ");

		if ( pInvoice.getSapFIDocumentDate() != 0 )
			 invoiceDqlUpdater.append("SET "+SAP_FI_DOC_DATE+" ="+ pInvoice.getSapFIDocumentDate() + " ");
		if ( pInvoice.getSapMMDocumentDate() != 0 )
			 invoiceDqlUpdater.append("SET "+SAP_MM_DOC_DATE+" ="+ pInvoice.getSapMMDocumentDate() + " ");
		invoiceDqlUpdater.append("SET "+FREIGHT_COST+" ="+ pInvoice.getFreightCosts() + " ");
		invoiceDqlUpdater.append("SET "+PACKAGING_COST+" ="+ pInvoice.getPackagingCosts() + " ");
		invoiceDqlUpdater.append("SET "+PAYMENT_CONDITION+" ='"+ pInvoice.getPaymentCondition() + "' ");
		invoiceDqlUpdater.append("SET "+SEL_THRESHOLD_AMOUNT+" ='"+ pInvoice.getSelectedThresholdAmount() + "' ");
		invoiceDqlUpdater.append("SET "+SELECTED_IBAN+" ='"+ pInvoice.getSelectedIban() + "' ");
		invoiceDqlUpdater.append("SET "+SCANNING_REFERENCE+" ='"+ pInvoice.getScanningReference() + "' ");
		if ( !pInvoice.getScanningDate().equalsIgnoreCase("nulldate") )
			invoiceDqlUpdater.append("SET "+SCANNING_DATE+" ="+ dateUtils.stringToDate(pInvoice.getScanningDate(), "dd-mm-yyyy") + " ");
		invoiceDqlUpdater.append("SET "+COMPANY_TAX_NUMBER+" ='"+ pInvoice.getCompanyTaxNumber() + "' ");
		invoiceDqlUpdater.append("SET "+COMPANU_VAT_NUMBER+" ='"+ pInvoice.getCompanyVatNumber() + "' ");
		invoiceDqlUpdater.append("SET "+SAP_INVOICE_CREATOR+" ='"+ pInvoice.getSapInvoiceCreator() + "' ");
		invoiceDqlUpdater.append("SET "+SALES_ORDER_NUMBER+" ='"+ pInvoice.getSalesOrderNumber() + "' ");
		invoiceDqlUpdater.append("SET "+SALES_ORDER_POSITION+" ='"+ pInvoice.getSalesOrderPosition() + "' ");
		//TODO
		//GOOD_RECEIPTS
		invoiceDqlUpdater.append(" WHERE r_object_id = '"+ pInvoice.getrObjectId() + "' ");
		logger.debug("Executing: " + invoiceDqlUpdater.toString());
		DQLquery.setDQL(invoiceDqlUpdater.toString());
		return DQLquery;
	}
	
	private IDfQuery updateInvoiceLines(Invoice pInvoice,IDfQuery DQLquery){
		// Updating invoiceLines
				// Getting invoice lines
				// check if the invoice line exist then update else create the invoice
				// line
//				boolean updatingLine = false;
//				IDfQuery queryGetInvoiceLine = new DfQuery();
//				queryGetInvoiceLine.setDQL(DQL_QUERY_RETRIEVE_INVOICE_LINE
//						+ pInvoice.getrObjectId() + "'");
//				IDfCollection invoiceLineCol = queryGetInvoiceLine.execute(session,
//						IDfQuery.DF_READ_QUERY);
//				if (null != invoiceLineCol) {
//					while (invoiceLineCol.next()) {
//						for (InvoiceLine invoiceLine : pInvoice.getInvoiceLines()) {
//							if (invoiceLine.getrObjectId().equalsIgnoreCase(
//									invoiceLineCol.getString("r_object_id"))) {
//								StringBuilder invoiceLinesDqlUpdater = new StringBuilder("UPDATE "+NAMESPACE+"_invoice_line OBJECTS ");
//								invoiceLinesDqlUpdater.append("SET assignement_vendor_number="+ invoiceLineCol.getString("assignement_vendor_number")+ " ");
//								
//								invoiceLinesDqlUpdater.append("SET cost_center="+ invoiceLineCol.getString("cost_center") + " ");
//								invoiceLinesDqlUpdater.append("SET gl_item_text="+ invoiceLineCol.getString("gl_item_text")+ " ");
//								invoiceLinesDqlUpdater.append("SET gl_account="+ invoiceLineCol.getString("gl_account") + " ");
//								invoiceLinesDqlUpdater.append("SET gl_account_number="+ invoiceLineCol.getString("gl_account_number")+ " ");
//								invoiceLinesDqlUpdater.append("SET gl_sales_tax_code="+ invoiceLineCol.getString("gl_sales_tax_code")+ " ");
//								invoiceLinesDqlUpdater.append("SET internal_order="+ invoiceLineCol.getString("internal_order")+ " ");
//								invoiceLinesDqlUpdater.append("SET material="+ invoiceLineCol.getString("material") + " ");
//								invoiceLinesDqlUpdater.append("SET partner_profit_center="+ invoiceLineCol.getString("partner_profit_center")+ " ");
//								invoiceLinesDqlUpdater.append("SET plant="+ invoiceLineCol.getString("plant") + " ");
//								invoiceLinesDqlUpdater.append("SET profit_center="+ invoiceLineCol.getString("profit_center")+ " ");
//								invoiceLinesDqlUpdater.append("SET quantity="+ invoiceLineCol.getString("quantity") + " ");
//								invoiceLinesDqlUpdater.append("SET sales_order="+ invoiceLineCol.getString("sales_order") + " ");
//								invoiceLinesDqlUpdater.append("SET sales_tax_order="+ invoiceLineCol.getString("sales_tax_order")+ " ");
//								//TODO
////								invoiceLinesDqlUpdater.append("SET tax_gl_account="+ invoiceLineCol.getString("tax_gl_account")	+ " ");
////								invoiceLinesDqlUpdater.append("SET tax_item_number="+ invoiceLineCol.getString("tax_item_number")+ " ");
////								invoiceLinesDqlUpdater.append("SET base_line_date="+ invoiceLineCol.getString("base_line_date")	+ " ");
////								invoiceLinesDqlUpdater.append("SET tax_rate="+ invoiceLineCol.getString("tax_rate") + " ");
//								invoiceLinesDqlUpdater.append("SET vendor_item_number="+ invoiceLineCol.getString("vendor_item_number") + " ");
//								invoiceLinesDqlUpdater.append("SET wbs="+ invoiceLineCol.getString("wbs") + " ");
//								invoiceLinesDqlUpdater.append(" WHERE r_object_id = '"+ invoiceLineCol.getString("r_object_id")+ "' ");
//								logger.debug("Updating invoice line: "+ invoiceLinesDqlUpdater.toString());
//								DQLquery.setDQL(invoiceLinesDqlUpdater.toString());
//								DQLquery.execute(dctmInstance.getSession(),
//										IDfQuery.DF_EXEC_QUERY);
//								updatingLine = true;
//							}
//						}
//						if (updatingLine == true)
//							updatingLine = false;
//						else {
//							// Bloc to create an invoice line if this one doesn't exist
//							StringBuilder invoiceLinesDqlCreator = new StringBuilder("CREATE "+NAMESPACE+"_invoice_line OBJECTS ");
//							invoiceLinesDqlCreator.append("SET amount="+ invoiceLineCol.getString("amount") + " ");
//							invoiceLinesDqlCreator.append("SET assignement_vendor_number="+ invoiceLineCol.getString("assignement_vendor_number")+ " ");
//							invoiceLinesDqlCreator.append("SET base_line_date="+ invoiceLineCol.getString("base_line_date") + " ");
//							invoiceLinesDqlCreator.append("SET cost_center="+ invoiceLineCol.getString("cost_center") + " ");
//							invoiceLinesDqlCreator.append("SET gl_item_text="+ invoiceLineCol.getString("gl_item_text") + " ");
//							invoiceLinesDqlCreator.append("SET gl_account="+ invoiceLineCol.getString("gl_account") + " ");
//							invoiceLinesDqlCreator.append("SET gl_account_number="+ invoiceLineCol.getString("gl_account_number")+ " ");
//							invoiceLinesDqlCreator.append("SET gl_sales_tax_code="+ invoiceLineCol.getString("gl_sales_tax_code")+ " ");
//							invoiceLinesDqlCreator.append("SET internal_order="+ invoiceLineCol.getString("internal_order") + " ");
//							invoiceLinesDqlCreator.append("SET material="+ invoiceLineCol.getString("material") + " ");
//							invoiceLinesDqlCreator.append("SET partner_profit_center="+ invoiceLineCol.getString("partner_profit_center")+ " ");
//							invoiceLinesDqlCreator.append("SET plant="+ invoiceLineCol.getString("plant") + " ");
//							invoiceLinesDqlCreator.append("SET profit_center="+ invoiceLineCol.getString("profit_center") + " ");
//							invoiceLinesDqlCreator.append("SET quantity="+ invoiceLineCol.getString("quantity") + " ");
//							invoiceLinesDqlCreator.append("SET sales_order="+ invoiceLineCol.getString("sales_order") + " ");
//							invoiceLinesDqlCreator.append("SET sales_tax_order="+ invoiceLineCol.getString("sales_tax_order") + " ");
//							invoiceLinesDqlCreator.append("SET tax_gl_account="+ invoiceLineCol.getString("tax_gl_account") + " ");
//							invoiceLinesDqlCreator.append("SET tax_item_number="+ invoiceLineCol.getString("tax_item_number") + " ");
//							invoiceLinesDqlCreator.append("SET tax_rate="+ invoiceLineCol.getString("tax_rate") + " ");
//							invoiceLinesDqlCreator.append("SET vendor_item_number="+ invoiceLineCol.getString("vendor_item_number")+ " ");
//							invoiceLinesDqlCreator.append("SET wbs="+ invoiceLineCol.getString("wbs") + " ");
//							invoiceLinesDqlCreator.append(" WHERE r_object_id = '"+ invoiceLineCol.getString("r_object_id") + "' ");
//							logger.debug("Creating invoice line: "+ invoiceLinesDqlCreator.toString());
//							DQLquery.setDQL(invoiceLinesDqlCreator.toString());
//							DQLquery.execute(dctmInstance.getSession(),
//							IDfQuery.DF_EXEC_QUERY);
//						}
		//
//					}
//				}
		return DQLquery;
	}
	
	private IDfQuery updateSupplierDetail(Invoice pInvoice, IDfQuery DQLquery,IDfSession session){
		try
		{
			IDfQuery queryGetInvoiceSupplier = new DfQuery();
			queryGetInvoiceSupplier.setDQL(DQL_QUERY_RETRIEVE_INVOICE_SUPPLIER+ pInvoice.getrObjectId() + "'");
			IDfCollection invoiceSupplierDetail = queryGetInvoiceSupplier.execute(session, IDfQuery.DF_READ_QUERY);
			if (null != invoiceSupplierDetail)
			{
				if (pInvoice.getSupplierDetail()!= null){
					StringBuilder SupplierDqlUpdater = new StringBuilder("UPDATE "+NAMESPACE+"_supplier OBJECTS ");
					SupplierDqlUpdater.append("SET SUPPLIER_DEFAULT_IBAN=" +pInvoice.getSupplierDetail().getSupplierSelectedIban()+ " ");
					SupplierDqlUpdater.append("SET "+SUPPLIER_CPD+" =" +pInvoice.getSupplierDetail().isSupplierCPD()+ " ");
					SupplierDqlUpdater.append("SET "+SUPPLIER_INDUSTRY+" =" +pInvoice.getSupplierDetail().getSupplierIndustry() + " ");
					SupplierDqlUpdater.append("SET "+SUPPLIER_ADRESS+" =" +pInvoice.getSupplierDetail().getSupplierInvoiceAddress()+ " ");
					SupplierDqlUpdater.append("SET "+SUPPLIER_CITY+" =" +pInvoice.getSupplierDetail().getSupplierInvoiceCity() + " ");
					SupplierDqlUpdater.append("SET "+SUPPLIER_COUNTRY+" =" +pInvoice.getSupplierDetail().getSupplierInvoiceCountry() + " ");
					SupplierDqlUpdater.append("SET "+SUPPLIER_EMAIL+" =" +pInvoice.getSupplierDetail().getSupplierInvoiceEmail()+ " ");
					SupplierDqlUpdater.append("SET "+SUPPLIER_POST_CODE+" =" +pInvoice.getSupplierDetail().getSupplierInvoicePostCode() + " ");
					SupplierDqlUpdater.append("SET "+SUPPLIER_NAME+" =" +pInvoice.getSupplierDetail().getSupplierName() + " ");
					SupplierDqlUpdater.append("SET "+SUPPLIER_TAX_NUMBER+" =" +pInvoice.getSupplierDetail().getSupplierTaxNumber() + " ");
					SupplierDqlUpdater.append("SET "+SUPPLIER_VAT_NUMBER+" =" +pInvoice.getSupplierDetail().getSupplierVatNumber() + " ");
					SupplierDqlUpdater.append("SET "+SUPPLIER_NUMBER+" =" +pInvoice.getSupplierDetail().getSupplierNumber() + " ");
					SupplierDqlUpdater.append(" WHERE r_object_id = '" +pInvoice.getSupplierDetail().getrObjectId() + "' ");
					logger.debug("Updating Supplier: "+ SupplierDqlUpdater.toString());
					DQLquery.setDQL(SupplierDqlUpdater.toString());
				}
				else
				{
					 //Create invoice supplier
					StringBuilder supplierDqlCreator = new	 StringBuilder("CREATE supplier OBJECTS ");
					supplierDqlCreator.append("SET SUPPLIER_DEFAULT_IBAN=" +pInvoice.getSupplierDetail().getSupplierSelectedIban()+ " ");
					supplierDqlCreator.append("SET "+SUPPLIER_CPD+" =" +pInvoice.getSupplierDetail().isSupplierCPD()+ " ");
					supplierDqlCreator.append("SET "+SUPPLIER_INDUSTRY+" =" +pInvoice.getSupplierDetail().getSupplierIndustry() + " ");
					supplierDqlCreator.append("SET "+SUPPLIER_ADRESS+" =" +pInvoice.getSupplierDetail().getSupplierInvoiceAddress()+ " ");
					supplierDqlCreator.append("SET "+SUPPLIER_CITY+" =" +pInvoice.getSupplierDetail().getSupplierInvoiceCity() + " ");
					supplierDqlCreator.append("SET "+SUPPLIER_COUNTRY+" =" +pInvoice.getSupplierDetail().getSupplierInvoiceCountry() + " ");
					supplierDqlCreator.append("SET "+SUPPLIER_EMAIL+" =" +pInvoice.getSupplierDetail().getSupplierInvoiceEmail()+ " ");
					supplierDqlCreator.append("SET "+SUPPLIER_POST_CODE+" =" +pInvoice.getSupplierDetail().getSupplierInvoicePostCode() + " ");
					supplierDqlCreator.append("SET "+SUPPLIER_NAME+" =" +pInvoice.getSupplierDetail().getSupplierName() + " ");
					supplierDqlCreator.append("SET "+SUPPLIER_TAX_NUMBER+" =" +pInvoice.getSupplierDetail().getSupplierTaxNumber() + " ");
					supplierDqlCreator.append("SET "+SUPPLIER_VAT_NUMBER+" =" +pInvoice.getSupplierDetail().getSupplierVatNumber() + " ");
					supplierDqlCreator.append("SET "+SUPPLIER_NUMBER+" =" +pInvoice.getSupplierDetail().getSupplierNumber() + " ");
					supplierDqlCreator.append(" WHERE r_object_id = '" +pInvoice.getSupplierDetail().getrObjectId() + "' ");
					 logger.debug("Creating Supplier: " +supplierDqlCreator.toString());
					 DQLquery.setDQL(supplierDqlCreator.toString());
					 }
			}
		} catch (DfException e) {
			logger.error("Error during update of Supplier Properties From DCTM "+ e.getMessage());
		}
		return DQLquery;
	}
	
	private IDfQuery updatePurchaseOrder(Invoice pInvoice,IDfQuery DQLquery,IDfSession session){
		// Updating Purchase Order Detail
		try
		{
			IDfQuery queryGetInvoicePO = new DfQuery();
			queryGetInvoicePO.setDQL(DQL_QUERY_RETRIEVE_INVOICE_PO+ pInvoice.getrObjectId() + "'");
			IDfCollection invoicePODetail = queryGetInvoicePO.execute(session,IDfQuery.DF_READ_QUERY);
			if (null != invoicePODetail) {
				while (invoicePODetail.next()) {
					StringBuilder poDetailDqlUpdater = new StringBuilder("UPDATE "+NAMESPACE+"_purchase_order OBJECTS ");
					poDetailDqlUpdater.append("SET "+PURCHASE_ORDER_DOC_DATE+" ="+ invoicePODetail.getString(PURCHASE_ORDER_DOC_DATE) + " ");
					poDetailDqlUpdater.append("SET "+PURCHASE_ORDER_NUMBER+" ="+ invoicePODetail.getString(PURCHASE_ORDER_NUMBER) + " ");
					poDetailDqlUpdater.append("SET "+PURCHASE_ORDER_NUMBER_POS+" ="+ invoicePODetail.getString(PURCHASE_ORDER_NUMBER_POS) + " ");
					poDetailDqlUpdater.append("SET "+PO_SUPPLIER_NAME+" ="+ invoicePODetail.getString(PO_SUPPLIER_NAME) + " ");
					poDetailDqlUpdater.append("SET "+PO_SUPPLIER_NUMBER+" ="+ invoicePODetail.getString(PO_SUPPLIER_NUMBER) + " ");
					poDetailDqlUpdater.append("SET "+PURCHASE_ORDER_DATE+" ="+ invoicePODetail.getString(PURCHASE_ORDER_DATE) + " ");
					poDetailDqlUpdater.append(" WHERE r_object_id = '"+ invoicePODetail.getString(R_OBJECT_ID) + "' ");
					logger.debug("Updating Purchase Order Properties: "+ poDetailDqlUpdater.toString());
					DQLquery.setDQL(poDetailDqlUpdater.toString());			
				}
			}
			// else{
			// //Creating PO_Detail Object
			// StringBuilder poDetailDqlCreator = new
			// StringBuilder("Create purchase_order OBJECTS ");
			// poDetailDqlCreator.append("SET document_type=" +
			// invoicePODetail.getString("document_type")+ " ");
			// poDetailDqlCreator.append("SET po_number=" +
			// invoicePODetail.getString("po_number")+ " ");
			// poDetailDqlCreator.append("SET po_number_position=" +
			// invoicePODetail.getString("po_number_position")+ " ");
			// poDetailDqlCreator.append("SET vendor_name=" +
			// invoicePODetail.getString("vendor_name")+ " ");
			// poDetailDqlCreator.append("SET vendor_number=" +
			// invoicePODetail.getString("vendor_number")+ " ");
			// poDetailDqlCreator.append(" WHERE r_object_id = '" +
			// invoiceSupplierDetail.getString("r_object_id") + "' ");
			// logger.debug("Updating PODetailDqlCreator: " +
			// poDetailDqlCreator.toString());
			// DQLquery.setDQL(poDetailDqlCreator.toString());
			// DQLquery.execute(dctmInstance.getSession(), IDfQuery.DF_EXEC_QUERY);
			// }
		} catch (DfException e) {
			logger.error("Error during update of Purchase Order Properties From DCTM "+ e.getMessage());
		}
		return DQLquery;
	}
	
	private IDfQuery updateBankInformations(Invoice pInvoice,IDfQuery DQLquery,IDfSession session){
		try
		{
			// Update Bank Detail
			boolean updatingBank = false;
			IDfQuery queryGetBankDetail = new DfQuery();
			queryGetBankDetail.setDQL(DQL_QUERY_RETRIEVE_BANK_DETAIL
					+ pInvoice.getrObjectId() + "'");
			IDfCollection invoiceBankDetail = queryGetBankDetail.execute(session,
					IDfQuery.DF_READ_QUERY);
			if (null != invoiceBankDetail) {
				while (invoiceBankDetail.next()) {
					for (BankDetails bankDetail : pInvoice.getListOfBankDetails()) {
						if (bankDetail.getrObjectId().equalsIgnoreCase(
								invoiceBankDetail.getString("r_object_id"))) {
							StringBuilder invoiceBanksDqlUpdater = new StringBuilder(
									"UPDATE "+NAMESPACE+"_invoice_bankinforma OBJECTS ");
							invoiceBanksDqlUpdater.append("SET bank_name="+ invoiceBankDetail.getString("bank_name")+ " ");
							invoiceBanksDqlUpdater.append("SET supplier_iban="+ invoiceBankDetail.getString("supplier_iban")+ " ");
							invoiceBanksDqlUpdater.append("SET supplier_name="+ invoiceBankDetail.getString("supplier_name")+ " ");
							invoiceBanksDqlUpdater.append(" WHERE r_object_id = '"+ invoiceBankDetail.getString("r_object_id")+ "' ");
							logger.debug("Updating bank detail : "+ invoiceBanksDqlUpdater.toString());
							DQLquery.setDQL(invoiceBanksDqlUpdater.toString());
							DQLquery.execute(dctmInstance.getSession(),IDfQuery.DF_EXEC_QUERY);
							updatingBank = true;
						}
					}
					if (updatingBank == true)
						updatingBank = false;
					else {
						// Bloc to create an invoice line if this one doesn't exist
						StringBuilder invoiceBankDqlCreator = new StringBuilder("CREATE "+NAMESPACE+"_invoice_bankinforma OBJECTS ");
						invoiceBankDqlCreator.append("SET bank_name="+ invoiceBankDetail.getString("bank_name") + " ");
						invoiceBankDqlCreator.append("SET supplier_iban="+ invoiceBankDetail.getString("supplier_iban")+ " ");
						invoiceBankDqlCreator.append("SET supplier_name="+ invoiceBankDetail.getString("supplier_name")+ " ");
						invoiceBankDqlCreator.append(" WHERE r_object_id = '"+ invoiceBankDetail.getString("r_object_id") + "' ");
						logger.debug("Creating Bank Detail: "+ invoiceBankDqlCreator.toString());
						DQLquery.setDQL(invoiceBankDqlCreator.toString());
						DQLquery.execute(dctmInstance.getSession(),IDfQuery.DF_EXEC_QUERY);
					}
	
				}
			}
		} catch (DfException e) {
			logger.error("Error during update of Bank Informations From DCTM "+ e.getMessage());
		}
		return DQLquery;
	}
	
	private IDfQuery updateApprovers(Invoice pInvoice,IDfQuery DQLquery,IDfSession session){	
	// Populate Approver list
		//	IDfQuery queryGetApprovers = new DfQuery();
		//	queryGetApprovers.setDQL(DQL_QUERY_RETRIEVE_APPROVER
		//			+ pInvoice.getrObjectId() + "'");
		//	IDfCollection invoiceApproverList = queryGetApprovers.execute(session,
		//			IDfQuery.DF_READ_QUERY);
		//	if (null != invoiceApproverList) {
		//		pInvoice.setApproverGroupList(new ArrayList<Message>());
		//		while (invoiceApproverList.next()) {
		//			Message message = new Message();
		//			message.setLogin(invoiceApproverList.getString("login"));
		//			message.setContentText(invoiceApproverList.getString("comment"));
		//			if (null != invoiceApproverList.getString("sequence"))
		//				message.setSequence(Integer.parseInt(invoiceApproverList
		//						.getString("sequence")));
		//			// TODO
		//			// verifier la bonne conversion de string vers date
		//			message.setDate(dateUtils.stringToDate(
		//					invoiceApproverList.getString("date"), "dd-mm-yyyy"));
		//			pInvoice.getApproverGroupList().add(message);
		//		}
		//	}
		return DQLquery;
	}

	private IDfQuery updateProcessors(Invoice pInvoice,IDfQuery DQLquery,IDfSession session){
	// Populate Processor list
		//	IDfQuery queryGetProcessors = new DfQuery();
		//	queryGetProcessors.setDQL(DQL_QUERY_RETRIEVE_PROCESSOR
		//			+ pInvoice.getrObjectId() + "'");
		//	IDfCollection invoiceProcessorList = queryGetProcessors.execute(
		//			session, IDfQuery.DF_READ_QUERY);
		//	if (null != invoiceProcessorList) {
		//		pInvoice.setProcessorDecision(new ArrayList<Message>());
		//		while (invoiceProcessorList.next()) {
		//			Message message = new Message();
		//			message.setLogin(invoiceApproverList.getString("login"));
		//			message.setContentText(invoiceApproverList.getString("comment"));
		//			if (null != invoiceApproverList.getString("sequence"))
		//				message.setSequence(Integer.parseInt(invoiceApproverList
		//						.getString("sequence")));
		//			// TODO
		//			// verifier la bonne conversion de string vers date
		//			message.setDate(dateUtils.stringToDate(
		//					invoiceApproverList.getString("date"), "dd-mm-yyyy"));
		//			pInvoice.getProcessorDecision().add(message);
		//		}
		//	}
		return DQLquery;
	}
}
