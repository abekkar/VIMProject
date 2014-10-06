package com.csc.vim.framework.dao.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.csc.vim.framework.model.Message;
import com.csc.vim.framework.dao.AbstractSapDao;
import com.csc.vim.framework.model.BankDetails;
import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.model.PurchaseOrder;
import com.csc.vim.framework.model.Supplier;
import com.csc.vim.framework.model.Threshold;
import com.csc.vim.framework.properties.Parameters;
import com.csc.vim.framework.util.MessageTypeEnum;
import com.csc.vim.framework.util.SapMessage;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.ext.DestinationDataProvider;

@Component
public class InvoiceSapDao extends AbstractSapDao {

	private static final String ARCHIV_CONNECTION_INSERT   = "ARCHIV_CONNECTION_INSERT";
	private static final String BAPI_INCOMINGINVOICE_SAVE  = "ZMM_BAPI_INCOMINGINVOICE_SAVE";
	private static final String ZMM_BAPI_RETRIEVE_TO_DCTM  = "ZMM_BAPI_RETRIEVE_TO_DCTM";
	private static final String ZFI_BAPI_ACC_DOCUMENT_POST = "ZFI_BAPI_ACC_DOCUMENT_POST";
	private static final String ACCOUNT_PAYABLE_STRUCTURE = "ACCOUNTPAYABLE";
	private static final String ACCOUNT_GL_STRUCTURE       = "ACCOUNT_GL";
	private static final String DOCUMENT_HEADER_STRUCTURE = "DOCUMENTHEADER";
	private static final String ACCOUNT_TAX_STRUCTURE = "ACCOUNTTAX";
	private static final String CURRENCY_AMOUNT_STRUCTURE = "CURRENCYAMOUNT";
	private static final String THRESHOLD_STRUCTURE = "THRESHOLD_DESC";
	
	private static final String DOC_TYPE = "KN";
	private static final String COMPANY_CODE = "0011";
	private static final String SAP_X_VALUE = "X";
	private static final String SAP_H_VALUE = "H";
	private static final String SAP_S_VALUE = "S";
	
	@Autowired
	private Parameters parametersProperties ;

	/*
	 * Structure Properties
	 */
	 private static final String PO_HEADER_STRUCTURE= "PO_HEADER";
	 private static final String SAP_IBAN_STRUCTURE= "SAP_IBAN";
	 private static final String SAP_IBAN_INPUT_STRUCTURE= "T_IBAN";
	 //private static final String PO_ITEMS_STRUCTURE= "PO_ITEMS";
	 //private static final String THRESHOLD_AMOUNT_STRUCTURE = "THRESHOLD_AMOUNT";
	 private static final String BLOCKING_CODE_STRUCTURE= "BLOCKING_CODE";
	 private static final String ADDITIONAL_HEADERDATA_STRUCTURE ="ADDITIONALHEADERDATA";
	 
	/*
	 * Supplier Properties
	 */
	 
	private static final String SUPPLIER_CPD = "CPD";
	private static final String SUPPLIER_INDUSTRY  = "INDUSTRY"; 
	private static final String SUPPLIER_ADRESS    = "INV_SUPPLIER_ADD";
	private static final String SUPPLIER_CITY      = "INV_SUPPLIER_CITY";
	private static final String SUPPLIER_NAME      = "INV_SUPPLIER_NAME";
	private static final String INVOICE_FAMILY     = "INVOICE_FAMILY";
	private static final String SUPPLIER_COUNTRY   = "INV_SUPPLIER_COUNTRY";
	private static final String SUPPLIER_EMAIL     = "INV_SUPPLIER_EMAIL";
	private static final String SUPPLIER_POST_CODE = "INV_SUPPLIER_PCODE";
	private static final String SUPPLIER_TAXNUMBER = "SUPPLIER_TAXNUMBER";
	private static final String SUPPLIER_VATNUMBER = "SUPPLIER_VATNUMBER";
	private static final String SUPPLIER_IBAN      = "IBAN";
	
	/*
	 * PO Properties
	 */
	
	private static final String PO_NUMBER = "PO_NUMBER";
	private static final String PO_TYPE   = "DOC_TYPE";
	private static final String VEND_NAME = "VEND_NAME";
	
	/*
	 * Invoice Properties
	 */
	
	private static final String INVOICE_CATEGORY    = "INVOICE_CATEGORY";
	private static final String BLOKING_CODE        = "ZAHLS"; 
	private static final String THRESHOLD_AMOUNT    = "THRESHOLDAMOUNT";
	private static final String THRESHOLDKEY        = "THRESHOLDKEY";
	private static final String THRESHOLD_DESCR     = "THRESHOLD_DESCR";
	private static final String THRESHOLD_UNIT      = "THRESHOLD_UNIT";    
	private static final String PAY_CONDITION       = "PAY_CONDITION";
	private static final String INV_SUPPLIER_ORIGIN = "INV_SUPPLIER_ORIGIN";
	private static final String UCT                 = "UCT";
	private static final String LCT                 = "LCT";
	private static final String NET_AMOUNT_EUR      = "NET_AMOUNT_EUR";
	
	/*
	 * Processor Properties 
	 */
	
	private static final String PROCESSOR            = "PROCESSOR";
	private static final String DISPATCHER_NAME      = "DISPATCHER_NAME";
	
	/*
	 * Approver Properties 
	 */
	
	private static final String APPROVER = "APPROVER";
	private static final String APPROVER_NAME = "APPROVER_NAME";
	private static final String PROCESSOR_TABLE = "PROCESSOR_TABLE";
	private static final String APPROVER_TABLE = "APPROVER_TABLE";
	
	/*
	 * Return ZMM_BAPI_INCOMINGINVOICE_SAVE Properties
	 */
	
	private static final String INVOICE_DOC_NUMBER = "INVOICEDOCNUMBER";
	private static final String FISCAL_YEAR = "FISCALYEAR";
	/**
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pInvoiceContent Invoice: The invoice that need to be link between DCTM and SAP
	 * @param pParentId: Not use for SAP (Only DCTM use this
	 */
	@Override
	public String linkDocument(Invoice pInvoice) {

		// The ID of DCTM invoice
		String ARC_DOC_ID = parametersProperties.getDctmRepositoryName() + ":id," + pInvoice.getrObjectId();

		logger.info("Executing: "+ARCHIV_CONNECTION_INSERT+" (OBJECT_ID=" + pInvoice.getSapMMDocumentNumber() + ", with ARC_DOC_ID=" + ARC_DOC_ID);

		JCoFunction sapFunction = null;
		try {
			sapFunction = destination.getRepository().getFunction(ARCHIV_CONNECTION_INSERT);
		} catch (JCoException e) {
			e.printStackTrace();
		}

		sapFunction.getImportParameterList().setValue("ARCHIV_ID", parametersProperties.getARCHIV_ID());		// ID of the Archive
		sapFunction.getImportParameterList().setValue("ARC_DOC_ID",ARC_DOC_ID);						            // <RepositoryName>:id,<r_object_id>
		sapFunction.getImportParameterList().setValue("AR_OBJECT", parametersProperties.getAR_OBJECT());		// Custom Type Document created in SAP
		if (pInvoice.getSapFIDocumentNumber()!= null)
			sapFunction.getImportParameterList().setValue("OBJECT_ID", pInvoice.getSapFIDocumentNumber());																	
		else if (pInvoice.getSapMMDocumentNumber() != null)
			sapFunction.getImportParameterList().setValue("OBJECT_ID", pInvoice.getSapMMDocumentNumber());
		else 
			return "0";
		sapFunction.getImportParameterList().setValue("SAP_OBJECT", parametersProperties.getSAP_OBJECT());		// Object Type in SAP
		sapFunction.getImportParameterList().setValue("DOC_TYPE", parametersProperties.getMM_DOC_TYPE());		// The format of the attached File


		try {
			sapFunction.execute(destination);
		} catch (JCoException e) {
			e.printStackTrace();
		}

		return "0";
	}

	/**
	 * Create an invoice into SAP and update the status of the invoice with PO
	 * 
	 * @since 1.0
	 * @author abekkar
	 * @param pInvoice	Invoice		The Invoice to create into SAP
	 * @return String	The ID of the invoice into SAP. This ID can be a Document MM Id (Invoice with PO) or a Document FI Id (Invoice without PO)
	 * @throws IOException, JCoException
	 */
	public Invoice createWithPurchaseOrder(Invoice pInvoice) {
		try {
			connectToSap();
		} catch (JCoException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 

		logger.debug("Executing " + BAPI_INCOMINGINVOICE_SAVE);

		JCoFunction function = null;
		try {
			function = destination.getRepository().getFunction(BAPI_INCOMINGINVOICE_SAVE);
		} catch (JCoException e1) {
			e1.printStackTrace();
		}

		// ==============================================================================================
		// ======================================== 1/ SET INUPUT =======================================
		// ==============================================================================================
		function.getImportParameterList().setValue("REFDOCCATEGORY", pInvoice.getInvoiceCategory());
		//TODO
		//		function.getImportParameterList().setValue("ZLIFNR", pInvoice.getSupplierTaxNumber());
		// *****************************
		// *** Structure HEADERDATA ****
		// *****************************
		com.sap.conn.jco.JCoStructure strucHeaderData = function.getImportParameterList().getStructure("HEADERDATA");
		//TODO
		//Modify the format
		// Document date
		strucHeaderData.setValue("DOC_DATE", pInvoice.getInvoiceDate());
		strucHeaderData.setValue("DOC_TYPE", DOC_TYPE);
		// Invoice Reference
		strucHeaderData.setValue("REF_DOC_NO", pInvoice.getInvoiceReference());
		// Invoice (X) or Asset
		if ( pInvoice.getInvoiceType() == "0" )
			strucHeaderData.setValue("INVOICE_IND", SAP_X_VALUE);
		else
			strucHeaderData.setValue("INVOICE_IND", "");
		// SAP CODE of the client company
		strucHeaderData.setValue("COMP_CODE", COMPANY_CODE);
		//Invoice Currency
		strucHeaderData.setValue("CURRENCY", pInvoice.getInvoiceCurrency());
		// physical reference provided by Captiva
		strucHeaderData.setValue("HEADER_TXT", pInvoice.getScanningReference());
		//Correspond a la date de base soit la date de calcul pour le delai de paiement (=date de reception)
		//Recuperation de la date du document
		strucHeaderData.setValue("PSTNG_DATE", pInvoice.getScanningDate());
		//TODO
		//Verify specification 
		//Payment Mode
		strucHeaderData.setValue("PYMT_METH", parametersProperties.getPaymentMode());
		//Payment Condition
		strucHeaderData.setValue("PMNTTRMS", parametersProperties.getPaymentCondition());	
		// GRoss Amount
		DecimalFormat transform = new DecimalFormat("0.00");
		String gross_amount = transform.format(pInvoice.getInvoiceGrossAmount());
		gross_amount = gross_amount.replace(",", ".");
		strucHeaderData.setValue("GROSS_AMOUNT", Double.parseDouble(gross_amount));
		//TODO
		//Validate VAT_AMOUNT Field in SAP BAPI
		//VAT AMOUNT
		String vat_amount = transform.format(pInvoice.getInvoiceVatAmount());
		vat_amount = vat_amount.replace(",", ".");
		strucHeaderData.setValue("VAT_AMOUNT", Double.parseDouble(vat_amount));
		
		//TODO 
		//ADD Profit Center
//		strucHeaderData.setValue("PROFIT_CENTER", "");
		
		// ****************************************
		// *** ADDITIONALHEADERDATA STRUCTURE  ****
		// ****************************************
		com.sap.conn.jco.JCoStructure strucAdditionalHeadData = function.getImportParameterList().getStructure(ADDITIONAL_HEADERDATA_STRUCTURE);
		
		// DEL_COSTS (Indicator: freight and packaging cost)
		strucAdditionalHeadData.setValue("DEL_COSTS", pInvoice.getPackagingCosts()+pInvoice.getFreightCosts());
		
		//ASSIGN_DELIV (Indicator: Delivery item assignment): X
		strucAdditionalHeadData.setValue("ASSIGN_DELIV", SAP_X_VALUE);
		
		// SEL_GOODS (Indicator: Goods invoice (X) /Service)
		strucAdditionalHeadData.setValue("SEL_GOODS", SAP_X_VALUE);
	
		//DELIV_POSTING (Posting logic for delivery items (Invoice=S/Credit memo=H):
		if ( pInvoice.getInvoiceType() == "0" )
			strucAdditionalHeadData.setValue("DELIV_POSTING", SAP_S_VALUE);
		else
			strucAdditionalHeadData.setValue("DELIV_POSTING", SAP_H_VALUE);
		
		//TODO
		// delete or uncomment
		//strucAdditionalHeadData.setValue("ASSIGN_RETURN", SAP_X_VALUE);
		//strucAdditionalHeadData.setValue("RETURN_POSTING", SAP_H_VALUE);

		// ***********************
		// *** TABLE SELECT PO ***
		// ***********************
		com.sap.conn.jco.JCoTable tableSelectPO = function.getTableParameterList().getTable("SELECTPO");
		tableSelectPO.appendRow();
		tableSelectPO.setValue("PO_NUMBER", pInvoice.getPurchaseOrder().getPoNumber());

		// Specifiy if the invoice is with PO+GR or only with PO
		if (null != pInvoice.getPurchaseOrder().getPoNumberPosition())
		tableSelectPO.setValue("PO_ITEM", pInvoice.getPurchaseOrder().getPoNumberPosition());

		// *********************
		// *** Table TAXDATA ***
		// *********************
		com.sap.conn.jco.JCoTable  tabTAXDATA = function.getTableParameterList().getTable("TAXDATA");
		tabTAXDATA.appendRow();
		tabTAXDATA.setValue("TAX_AMOUNT", pInvoice.getInvoiceVatAmount());

		// =============================================================================================
		// ======================================== 2/ EXECUTE =========================================
		// =============================================================================================

		try {
			function.execute(destination);
		} catch (JCoException e) {
			e.printStackTrace();
		}


		// =============================================================================================
		// ====================================== 3/ GET OUTPUT  =======================================
		// =============================================================================================

		com.sap.conn.jco.JCoTable resultTable = function.getTableParameterList().getTable("RETURN");

		// *******************************
		// NO ERROR => Get the Document ID
		// *******************************
		if (resultTable.getNumRows() == 0) {
			pInvoice.setSapMMDocumentNumber(function.getExportParameterList().getString(INVOICE_DOC_NUMBER));
			pInvoice.setSapMMDocumentDate(Integer.parseInt( function.getExportParameterList().getString(FISCAL_YEAR)));
			// Update the status of the invoice as "In SAP"
			pInvoice.setInvoiceStatus(helperConstant.INV_STATUS_SAP_OK);
			//TODO
			//retrieve blocking code from sap and set it in  sap_blocking_code Documentum's field
			//TODO 
			//ADD PAYMENT BLOCK CODE
			
			logger.warn("Invoice:" + function.getExportParameterList().getString(INVOICE_DOC_NUMBER) + " created with PO=" + pInvoice.getPurchaseOrder().getPoNumber());

		}

		// *******************************
		// ERROR => Get the list of error
		// *******************************
		else {
			com.sap.conn.jco.JCoTable sapMessageResultTable = function.getTableParameterList().getTable("RETURN");
			if (sapMessageResultTable.getNumRows() != 0) {
				pInvoice.setSapReturnMessage(new ArrayList<SapMessage>());
				for (int j = 0; j < sapMessageResultTable.getNumRows(); j++) {
					SapMessage sapMessageInstance = new SapMessage();
					sapMessageResultTable.setRow(j);
					sapMessageInstance.setMessageCode(sapMessageResultTable.getString("CODE"));
					sapMessageInstance.setMessageText(sapMessageResultTable.getString("MESSAGE"));
					if (sapMessageResultTable.getString("TYPE").equalsIgnoreCase(MessageTypeEnum.Succes.toString()))
						sapMessageInstance.setType(MessageTypeEnum.Succes);
					if (sapMessageResultTable.getString("TYPE").equalsIgnoreCase(MessageTypeEnum.Avert.toString()))
						sapMessageInstance.setType(MessageTypeEnum.Avert);
					if (sapMessageResultTable.getString("TYPE").equalsIgnoreCase(MessageTypeEnum.Abandon.toString()))
						sapMessageInstance.setType(MessageTypeEnum.Abandon);
					if (sapMessageResultTable.getString("TYPE").equalsIgnoreCase(MessageTypeEnum.Info.toString()))
						sapMessageInstance.setType(MessageTypeEnum.Info);
					if (sapMessageResultTable.getString("TYPE").equalsIgnoreCase(MessageTypeEnum.Error.toString())){
						sapMessageInstance.setType(MessageTypeEnum.Error);
						logger.warn("Invoice with PO=" + pInvoice.getPurchaseOrder().getPoNumber() + " error creating the invoice: " + sapMessageInstance.getMessageText());
					}
					pInvoice.getSapReturnMessage().add(sapMessageInstance);
				}
			}
			pInvoice.setInvoiceStatus(helperConstant.INV_STATUS_SAP_ERROR);
		}

		return pInvoice;
	}

	/**
	 * Create an invoice into SAP and update the status of the invoice without PO
	 * 
	 * @since 1.0
	 * @author abekkar
	 * @param pInvoice	Invoice		The Invoice to create into SAP
	 * @return String	The ID of the invoice into SAP. This ID can be a Document MM Id (Invoice with PO) or a Document FI Id (Invoice without PO)
	 * @throws IOException, JCoException
	 */
	public Invoice createWithoutPurchaseOrder(Invoice pInvoice) {
		try {
			connectToSap();
		} catch (JCoException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 

		logger.debug("Executing " + ZFI_BAPI_ACC_DOCUMENT_POST);

		JCoFunction function = null;
		try {
			function = destination.getRepository().getFunction(ZFI_BAPI_ACC_DOCUMENT_POST);
		} catch (JCoException e1) {
			e1.printStackTrace();
		}

		// ==============================================================================================
		// ======================================== 1/ SET INUPUT =======================================
		// ==============================================================================================
		function.getImportParameterList().setValue("REFDOCCATEGORY", pInvoice.getInvoiceCategory());

		// *********************************
		// *** Structure DOCUMENTHEADER ****
		// *********************************
		com.sap.conn.jco.JCoStructure strucDocumentHeader = function.getImportParameterList().getStructure(DOCUMENT_HEADER_STRUCTURE);
		//TODO
		//Modify the format
		// Document date
		strucDocumentHeader.setValue("BUS_ACT", parametersProperties.getBusAct());
		strucDocumentHeader.setValue("USERNAME", pInvoice.getSapInvoiceCreator());
		strucDocumentHeader.setValue("HEADER_TXT", pInvoice.getScanningReference());
		strucDocumentHeader.setValue("COMP_CODE", COMPANY_CODE );
		//TODO
		//format date to dd.mm.yyyy
		strucDocumentHeader.setValue("DOC_DATE", pInvoice.getInvoiceDate());
		strucDocumentHeader.setValue("PSTNG_DATE", pInvoice.getScanningDate());
		strucDocumentHeader.setValue("FISC_YEAR", dateUtils.getYear(dateUtils.stringToDate(pInvoice.getScanningDate(), "dd.mm.yyyyy")));
		strucDocumentHeader.setValue("FIS_PERIOD", dateUtils.getMonth(dateUtils.stringToDate(pInvoice.getScanningDate(), "dd.mm.yyyyy")) );
		//TODO
		//Bloc if for invoice or credit note
		//if ( pInvoice.getInvoiceType() == "0" || pInvoice.getInvoiceType() == "1")
			strucDocumentHeader.setValue("DOC_TYPE", parametersProperties.getMM_DOC_TYPE());
		//if it's a down payment activate the code below
		//else
		//	strucDocumentHeader.setValue("DOC_TYPE", parametersProperties.getDownPayment());
		strucDocumentHeader.setValue("REF_DOC_NO", pInvoice.getInvoiceReference());
		
		// *****************************
		// *** ACCOUNTGL STRUCTURE  ****
		// *****************************
		//TODO
		//Data retrieved from the invoice line
		//must create a "for" to process all the invoice lines 
		com.sap.conn.jco.JCoStructure strucAccountGL = function.getImportParameterList().getStructure(ACCOUNT_GL_STRUCTURE);
		strucAccountGL.setValue("ITEMNO_ACC", pInvoice.getInvoiceLines().get(0).getGlAccountNumber());
		strucAccountGL.setValue("GL_ACCOUNT",  pInvoice.getInvoiceLines().get(0).getGlAccount());
		strucAccountGL.setValue("ITEM_TXT",  pInvoice.getInvoiceLines().get(0).getGlItemText());
		strucAccountGL.setValue("ALLOC_NBR",  pInvoice.getInvoiceLines().get(0).getVendorAssignmentNumber());
		strucAccountGL.setValue("TAX_CODE",  pInvoice.getInvoiceLines().get(0).getSalestaxCode());
		strucAccountGL.setValue("COSTCENTER",  pInvoice.getInvoiceLines().get(0).getCostCenter());
		
		// **********************************
		// *** ACCOUNTPAYABLE STRUCTURE  ****
		// **********************************
		com.sap.conn.jco.JCoStructure strucAccountPayable = function.getImportParameterList().getStructure(ACCOUNT_PAYABLE_STRUCTURE);
		strucAccountPayable.setValue("VENDOR_NO", "");
		strucAccountPayable.setValue("BLINE_DATE",  pInvoice.getInvoiceLines().get(0).getBaseLineDate());
		strucAccountPayable.setValue("ALLOC_NBR", "");
		strucAccountPayable.setValue("ITEM_TEXT", "");
		strucAccountPayable.setValue("TAX_CODE", "");
		strucAccountPayable.setValue("ITEMNO_ACC",  pInvoice.getInvoiceLines().get(0).getVendorItemNumber());
		
		// **********************************
		// *** ACCOUNTTAX STRUCTURE  ****
		// **********************************
		
		com.sap.conn.jco.JCoStructure strucAccountTax = function.getImportParameterList().getStructure(ACCOUNT_TAX_STRUCTURE);
		strucAccountTax.setValue("GL_ACCOUNT",  pInvoice.getInvoiceLines().get(0).getGlTaxAccount());
		strucAccountTax.setValue("TAX_CODE",  pInvoice.getInvoiceLines().get(0).getTaxItemNumber());
		
		// **********************************
		// *** CURRENCYAMOUNT STRUCTURE  ****
		// **********************************
		com.sap.conn.jco.JCoStructure strucCurrencyAmount = function.getImportParameterList().getStructure(CURRENCY_AMOUNT_STRUCTURE);
		strucCurrencyAmount.setValue("ITEM_ACC", "");
		strucCurrencyAmount.setValue("CURRENCY", pInvoice.getInvoiceCurrency());
		strucCurrencyAmount.setValue("CURRENCY_ISO", pInvoice.getInvoiceCurrency());

		// =============================================================================================
		// ======================================== 2/ EXECUTE =========================================
		// =============================================================================================

		try {
			function.execute(destination);
		} catch (JCoException e) {
			e.printStackTrace();
		}


		// =============================================================================================
		// ====================================== 3/ GET OUTPUT  =======================================
		// =============================================================================================

		com.sap.conn.jco.JCoTable resultTable = function.getTableParameterList().getTable("RETURN");

		// *******************************
		// NO ERROR => Get the Document ID
		// *******************************
		if (resultTable.getNumRows() == 0) {
			//TODO
			//set FI Data
			pInvoice.setSapFIDocumentNumber(function.getExportParameterList().getString(INVOICE_DOC_NUMBER));
			pInvoice.setSapFIDocumentDate(Integer.parseInt( function.getExportParameterList().getString(FISCAL_YEAR)));
			// Update the status of the invoice as "In SAP"
			pInvoice.setInvoiceStatus(helperConstant.INV_STATUS_SAP_OK);
			
			logger.warn("Invoice:" + function.getExportParameterList().getString(INVOICE_DOC_NUMBER) + " created without PO=" + pInvoice.getPurchaseOrder().getPoNumber());

		}

		// *******************************
		// ERROR => Get the list of error
		// *******************************
		else {
			com.sap.conn.jco.JCoTable sapMessageResultTable = function.getTableParameterList().getTable("RETURN");
			if (sapMessageResultTable.getNumRows() != 0) {
				pInvoice.setSapReturnMessage(new ArrayList<SapMessage>());
				for (int j = 0; j < sapMessageResultTable.getNumRows(); j++) {
					SapMessage sapMessageInstance = new SapMessage();
					sapMessageResultTable.setRow(j);
					sapMessageInstance.setMessageCode(sapMessageResultTable.getString("CODE"));
					sapMessageInstance.setMessageText(sapMessageResultTable.getString("MESSAGE"));
					if (sapMessageResultTable.getString("TYPE").equalsIgnoreCase(MessageTypeEnum.Succes.toString()))
						sapMessageInstance.setType(MessageTypeEnum.Succes);
					if (sapMessageResultTable.getString("TYPE").equalsIgnoreCase(MessageTypeEnum.Avert.toString()))
						sapMessageInstance.setType(MessageTypeEnum.Avert);
					if (sapMessageResultTable.getString("TYPE").equalsIgnoreCase(MessageTypeEnum.Abandon.toString()))
						sapMessageInstance.setType(MessageTypeEnum.Abandon);
					if (sapMessageResultTable.getString("TYPE").equalsIgnoreCase(MessageTypeEnum.Info.toString()))
						sapMessageInstance.setType(MessageTypeEnum.Info);
					if (sapMessageResultTable.getString("TYPE").equalsIgnoreCase(MessageTypeEnum.Error.toString())){
						sapMessageInstance.setType(MessageTypeEnum.Error);
						logger.warn("Invoice with PO=" + pInvoice.getPurchaseOrder().getPoNumber() + " error creating the invoice: " + sapMessageInstance.getMessageText());
					}
					pInvoice.getSapReturnMessage().add(sapMessageInstance);
				}
			}
			pInvoice.setInvoiceStatus(helperConstant.INV_STATUS_SAP_ERROR);
		}

		return pInvoice;
	}

	/**
	 * Search an invoice. The invoice in input is coming from CAPTIVA.
	 * This function will add Sap's invoice information to this CAPTIVA Invoice and will return this same invoice object
	 * @author abekkar
	 * @since 1.0
	 * @param pInvoice 	the invoice we are searching. This invoice contains only data that are used as a search criteria
	 * @return Invoice 	the invoice in input with additional SAP data
	 * @throws IOException
	 * @throws JCoException
	 */
	public Invoice retrieveInvoice(Invoice pInvoice) {
		logger.debug("Executing " + ZMM_BAPI_RETRIEVE_TO_DCTM);
		
		// open a connection to SAP
		try {
			connectToSap();
		} catch (JCoException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JCoFunction function = null;
		try {
			function = destination.getRepository().getFunction(ZMM_BAPI_RETRIEVE_TO_DCTM);
		} catch (JCoException e) {
			e.printStackTrace();
		}

		// *************************************
		// *** SEt the input of the function ***
		// *************************************
		if (null != pInvoice.getPurchaseOrder() ) {
			function.getImportParameterList().setValue("PURCHASEORDER", pInvoice.getPurchaseOrder().getPoNumber());
		}
		if (pInvoice.getPurchaseOrder()!= null) {
			function.getImportParameterList().setValue("PO_POSITION", pInvoice.getPurchaseOrder().getPoNumberPosition());
		}
		if (null !=pInvoice.getSupplierDetail()){
			if (null != pInvoice.getSupplierDetail().getSupplierVatNumber()) {
				function.getImportParameterList().setValue("VAT_NUMBER", pInvoice.getSupplierDetail().getSupplierVatNumber());
			}
			if (null != pInvoice.getSupplierDetail().getSupplierTaxNumber()) {
				function.getImportParameterList().setValue("STEUER_NUMBER",pInvoice.getSupplierDetail().getSupplierTaxNumber());
			}
		}
		
		function.getImportParameterList().setValue("CURRENCY", pInvoice.getInvoiceCurrency());
		function.getImportParameterList().setValue("SALESORDER_NR", pInvoice.getSalesOrderNumber());
		function.getImportParameterList().setValue("INV_NETAMOUNT", pInvoice.getInvoiceNetAmount());
		function.getImportParameterList().setValue("ITEMS", SAP_X_VALUE);
		if (null != pInvoice.getListOfBankDetails() ){
			for (BankDetails bankInfo : pInvoice.getListOfBankDetails()) {
				function.getImportParameterList().getStructure(SAP_IBAN_INPUT_STRUCTURE).setValue("IBAN", bankInfo.getAccountIban());
			}
		}
		function.getImportParameterList().setValue("THRESHOLD_KEY", pInvoice.getSelectedThresholdAmount());
		function.getImportParameterList().setValue("VALIDATION_GROUP", pInvoice.getSelectedApprovalGroup());

		// *****************************
		//  *** Execute the function ***
		// *****************************
		try {
			function.execute(destination);
		} catch (JCoException e) {
			e.printStackTrace();
		}

		// ******************
		// *** GET OUTPUT ***
		// ******************
		Invoice invoiceInstance = pInvoice;
		// *************************
		// *** GET ERROR MESSAGE ***
		// *************************
		boolean errorExistence = false;
		com.sap.conn.jco.JCoTable sapMessageResultTable = function.getTableParameterList().getTable("RETURN");
		if (sapMessageResultTable.getNumRows() != 0) {
			invoiceInstance.setSapReturnMessage(new ArrayList<SapMessage>());
			for (int j = 0; j < sapMessageResultTable.getNumRows(); j++) {
				SapMessage sapMessageInstance = new SapMessage();
				sapMessageResultTable.setRow(j);
				if (sapMessageResultTable.getString("TYPE").equalsIgnoreCase(MessageTypeEnum.Succes.toString()))
					sapMessageInstance.setType(MessageTypeEnum.Succes);
				if (sapMessageResultTable.getString("TYPE").equalsIgnoreCase(MessageTypeEnum.Avert.toString()))
					sapMessageInstance.setType(MessageTypeEnum.Avert);
				if (sapMessageResultTable.getString("TYPE").equalsIgnoreCase(MessageTypeEnum.Abandon.toString()))
					sapMessageInstance.setType(MessageTypeEnum.Abandon);
				if (sapMessageResultTable.getString("TYPE").equalsIgnoreCase(MessageTypeEnum.Info.toString()))
					sapMessageInstance.setType(MessageTypeEnum.Info);
				if (sapMessageResultTable.getString("TYPE").equalsIgnoreCase(MessageTypeEnum.Error.toString())){
					sapMessageInstance.setType(MessageTypeEnum.Error);
					errorExistence = true;
				}
				sapMessageInstance.setMessageCode(sapMessageResultTable.getString("CODE"));
				sapMessageInstance.setMessageText(sapMessageResultTable.getString("MESSAGE"));
				invoiceInstance.getSapReturnMessage().add(sapMessageInstance);
			}
		}
		// if the returned result contains an error the data retrieving process is stoped
		if (errorExistence == true)
			return invoiceInstance;
		
		// **************************
		// *** GET INVOICE DATA   ***
		// **************************

		if (null != function.getExportParameterList().getString(INVOICE_FAMILY))
			invoiceInstance.setInvoiceFamily(Integer.parseInt(function.getExportParameterList().getString(INVOICE_FAMILY)));
		if (null != function.getExportParameterList().getString(INVOICE_CATEGORY))
			invoiceInstance.setInvoiceCategory(Integer.parseInt(function.getExportParameterList().getString(INVOICE_CATEGORY)));
		invoiceInstance.setCompanyCode(COMPANY_CODE);
		invoiceInstance.setSelectedIban(function.getExportParameterList().getStructure(SAP_IBAN_STRUCTURE).getString(SUPPLIER_IBAN));
//		invoiceInstance.setPaymentCondition(function.getExportParameterList().getString(PAY_CONDITION));
		invoiceInstance.setSapBlockingCode(function.getExportParameterList().getStructure(BLOCKING_CODE_STRUCTURE).getString(BLOKING_CODE));
		invoiceInstance.setInvoicecountryOrigin(function.getExportParameterList().getString(INV_SUPPLIER_ORIGIN));
		if (null != function.getExportParameterList().getString(UCT))
			invoiceInstance.setInvoiceUCT(Double.parseDouble(function.getExportParameterList().getString(UCT)));
		if (null != function.getExportParameterList().getString(LCT))
			invoiceInstance.setInvoiceLCT(Double.parseDouble(function.getExportParameterList().getString(LCT)));
		if (null != function.getExportParameterList().getString(NET_AMOUNT_EUR))
			invoiceInstance.setInvoiceNetAmountEur(Double.parseDouble(function.getExportParameterList().getString(NET_AMOUNT_EUR)));
		//Getting the threshold list
		if (function.getTableParameterList().getTable(THRESHOLD_STRUCTURE).getNumRows() > 0){
			if (null == invoiceInstance.getListOfThreshold()){
				invoiceInstance.setListOfThreshold(new ArrayList<Threshold>());
				for (int i = 0; i < function.getTableParameterList().getTable(THRESHOLD_STRUCTURE).getNumRows(); i++) {
					function.getTableParameterList().getTable(THRESHOLD_STRUCTURE).setRow(i);
					Threshold thresholdInstance = new Threshold();
					thresholdInstance.setThresholdKey(function.getTableParameterList().getTable(THRESHOLD_STRUCTURE).getString(THRESHOLDKEY));
					thresholdInstance.setThresholdDesc(function.getTableParameterList().getTable(THRESHOLD_STRUCTURE).getString(THRESHOLD_DESCR));
					thresholdInstance.setThresholdAmount(function.getTableParameterList().getTable(THRESHOLD_STRUCTURE).getString(THRESHOLD_AMOUNT));
					thresholdInstance.setThresholdUnit(function.getTableParameterList().getTable(THRESHOLD_STRUCTURE).getString(THRESHOLD_UNIT));
					invoiceInstance.getListOfThreshold().add(thresholdInstance);
				}
			}
			else{
				
			}
		}
		// *****************************
		// **** GET APPROVER LIST   ****
		// *****************************
		if (function.getTableParameterList().getTable(APPROVER_TABLE).getNumRows() > 0){
			invoiceInstance.getApproverGroupList().clear();
			invoiceInstance.setApproverGroupList(new ArrayList<Message>());
			for (int i = 0; i < function.getTableParameterList().getTable(APPROVER_TABLE).getNumRows(); i++) {
				function.getTableParameterList().getTable(APPROVER_TABLE).setRow(i);
				Message approverMessageInstance = new Message();
				approverMessageInstance.setLogin(function.getTableParameterList().getTable(APPROVER_TABLE).getString(APPROVER_NAME));
				approverMessageInstance.setRole(APPROVER);
				approverMessageInstance.setDate(new Date());
				invoiceInstance.getApproverGroupList().add(approverMessageInstance);
			}
		}
		
		// *****************************
		// **** GET PROCESSOR LIST   ***
		// *****************************
		if (function.getTableParameterList().getTable(PROCESSOR_TABLE).getNumRows() > 0){
			invoiceInstance.getProcessorDecision().clear();
			invoiceInstance.setProcessorDecision(new ArrayList<Message>());
			for (int i = 0; i < function.getTableParameterList().getTable(PROCESSOR_TABLE).getNumRows(); i++) {
				function.getTableParameterList().getTable(PROCESSOR_TABLE).setRow(i);
				Message processorMessageInstance = new Message();
				processorMessageInstance.setLogin(function.getTableParameterList().getTable(PROCESSOR_TABLE).getString(DISPATCHER_NAME));
				processorMessageInstance.setRole(PROCESSOR);
				processorMessageInstance.setDate(new Date());
				invoiceInstance.getApproverGroupList().add(processorMessageInstance);
			}
		}
		
		// *****************************
		// *** GET SUPPLIER DETAIL   ***
		// *****************************
		if (invoiceInstance.getSupplierDetail()==null){
			Supplier supplierDetailInstance = new Supplier();
			supplierDetailInstance.setSupplierName(function.getExportParameterList().getString(SUPPLIER_NAME));
			supplierDetailInstance.setSupplierIndustry(function.getExportParameterList().getString(SUPPLIER_INDUSTRY));
			supplierDetailInstance.setSupplierInvoiceAddress(function.getExportParameterList().getString(SUPPLIER_ADRESS));
			supplierDetailInstance.setSupplierInvoiceCity(function.getExportParameterList().getString(SUPPLIER_CITY));
			supplierDetailInstance.setSupplierInvoicePostCode(function.getExportParameterList().getString(SUPPLIER_POST_CODE));
			supplierDetailInstance.setSupplierInvoiceCountry(function.getExportParameterList().getString(SUPPLIER_COUNTRY));
			supplierDetailInstance.setSupplierInvoiceEmail(function.getExportParameterList().getString(SUPPLIER_EMAIL));
			if(null != pInvoice.getSupplierDetail()){
				supplierDetailInstance.setSupplierTaxNumber(pInvoice.getSupplierDetail().getSupplierTaxNumber());
				supplierDetailInstance.setSupplierVatNumber(pInvoice.getSupplierDetail().getSupplierVatNumber());
			}
			if (null != function.getExportParameterList().getString(SUPPLIER_CPD)){
				if (function.getExportParameterList().getString(SUPPLIER_CPD).equalsIgnoreCase("0"))
					supplierDetailInstance.setSupplierCPD(false);
				else
					supplierDetailInstance.setSupplierCPD(true);
			}
			invoiceInstance.setSupplierDetail(supplierDetailInstance);
		}
		else {
			invoiceInstance.getSupplierDetail().setSupplierName(function.getExportParameterList().getString(SUPPLIER_NAME));
			invoiceInstance.getSupplierDetail().setSupplierIndustry(function.getExportParameterList().getString(SUPPLIER_INDUSTRY));
			invoiceInstance.getSupplierDetail().setSupplierInvoiceAddress(function.getExportParameterList().getString(SUPPLIER_ADRESS));
			invoiceInstance.getSupplierDetail().setSupplierInvoiceCity(function.getExportParameterList().getString(SUPPLIER_CITY));
			invoiceInstance.getSupplierDetail().setSupplierInvoicePostCode(function.getExportParameterList().getString(SUPPLIER_POST_CODE));
			invoiceInstance.getSupplierDetail().setSupplierInvoiceCountry(function.getExportParameterList().getString(SUPPLIER_COUNTRY));
			invoiceInstance.getSupplierDetail().setSupplierInvoiceEmail(function.getExportParameterList().getString(SUPPLIER_EMAIL));
			if(null != pInvoice.getSupplierDetail()){
				invoiceInstance.getSupplierDetail().setSupplierTaxNumber(pInvoice.getSupplierDetail().getSupplierTaxNumber());
				invoiceInstance.getSupplierDetail().setSupplierVatNumber(pInvoice.getSupplierDetail().getSupplierVatNumber());
			}
			if (null != function.getExportParameterList().getString(SUPPLIER_CPD))
				if (function.getExportParameterList().getString(SUPPLIER_CPD).equalsIgnoreCase("0"))
					invoiceInstance.getSupplierDetail().setSupplierCPD(false);
				else
					invoiceInstance.getSupplierDetail().setSupplierCPD(true);
		}

		// ***********************************
		// *** GET PURCHASE ORDER DETAIL   ***
		// ***********************************
		if (null == invoiceInstance.getPurchaseOrder()){
			PurchaseOrder purchaseOrderInstance = new PurchaseOrder();
			purchaseOrderInstance.setPoDocumentType(function.getExportParameterList().getStructure(PO_HEADER_STRUCTURE).getString(PO_TYPE));
			purchaseOrderInstance.setPoNumber(function.getExportParameterList().getStructure(PO_HEADER_STRUCTURE).getString(PO_NUMBER));
			if (null != pInvoice.getPurchaseOrder()){
				purchaseOrderInstance.setPoNumberPosition(pInvoice.getPurchaseOrder().getPoNumberPosition());
				purchaseOrderInstance.setPoNumber(pInvoice.getPurchaseOrder().getPoNumber());
			}
			purchaseOrderInstance.setSupplierName(function.getExportParameterList().getStructure(PO_HEADER_STRUCTURE).getString(VEND_NAME));
			invoiceInstance.setPurchaseOrder(purchaseOrderInstance);
		}
		else{
			invoiceInstance.getPurchaseOrder().setPoDocumentType(function.getExportParameterList().getStructure(PO_HEADER_STRUCTURE).getString(PO_TYPE));
			invoiceInstance.getPurchaseOrder().setPoNumber(function.getExportParameterList().getStructure(PO_HEADER_STRUCTURE).getString(PO_NUMBER));
			if (null != pInvoice.getPurchaseOrder()){
				invoiceInstance.getPurchaseOrder().setPoNumberPosition(pInvoice.getPurchaseOrder().getPoNumberPosition());
				invoiceInstance.getPurchaseOrder().setPoNumber(pInvoice.getPurchaseOrder().getPoNumber());
			}
			invoiceInstance.getPurchaseOrder().setSupplierName(function.getExportParameterList().getStructure(PO_HEADER_STRUCTURE).getString(VEND_NAME));
		}
			
		logger.debug("Executing " + invoiceInstance.getInvoiceReference());
		return invoiceInstance;
	}


	/**
	 * Initiate a connection with SAP. The connection object is stored into the class variable: destination
	 * @author abekkar
	 * @throws JCoException,IOException
	 * @since 1.0
	 * @throws Exception
	 */
	@Override
	protected JCoDestination connectToSap() throws JCoException, IOException {
		if (destination == null ) {
			Properties connectProperties = new Properties();

			String jco_ahost = null;
			String jco_sysnr = null;
			String jco_client = null;
			String jco_user = null;
			String jco_passwd = null;
			String jco_lang = null;

				jco_ahost = parametersProperties.getVal_sap_jco_ahost();
				jco_sysnr = parametersProperties.getVal_sap_jco_sysnr();
				jco_client = parametersProperties.getVal_sap_jco_client();
				jco_user = parametersProperties.getVal_sap_jco_user();
				jco_passwd = parametersProperties.getVal_sap_jco_passwd();
				jco_lang = parametersProperties.getVal_sap_jco_lang();


			connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, jco_ahost);
			connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  jco_sysnr );
			connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, jco_client);
			connectProperties.setProperty(DestinationDataProvider.JCO_USER,  jco_user );
			connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, jco_passwd);
			connectProperties.setProperty(DestinationDataProvider.JCO_LANG, jco_lang);

			try {
				 createDestinationDataFile(parametersProperties.getSapDataFile(), connectProperties);
				 destination = JCoDestinationManager.getDestination(parametersProperties.getSapDataFile());
				 logger.debug("Connect succesfully to SAP");
			} catch (JCoException e) {
				e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		return destination;
		
	}
	
	

}
