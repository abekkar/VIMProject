package com.csc.vim.framework.invoice.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.csc.vim.framework.bankinformation.model.BankingInformation;
import com.csc.vim.framework.common.model.DematObject;
import com.csc.vim.framework.model.InvoiceLine;
import com.csc.vim.framework.model.Message;
import com.csc.vim.framework.model.PurchaseOrder;
import com.csc.vim.framework.model.Threshold;
import com.csc.vim.framework.properties.BlockingCodeEnumList;
import com.csc.vim.framework.supplier.model.Supplier;
import com.csc.vim.framework.util.SapMessage;

@lombok.Getter
@lombok.Setter
public class Invoice extends DematObject {

private List<Threshold> listOfThreshold;
	
	public List<String> message = new ArrayList<String>();
	
	private String sapBlockingCode;
	
	private Double invoiceNetAmountEur;
	
	private int invoiceNetAmountThreshold;
	
	private Double invoiceUCT;
	
	private Double invoiceLCT;
	
	private List<String> invoiceIban;
	//By SAP
	private List<Message> processorDecision;
	
	private String firstLevelController;
	private String GlobalLevelController;
	
	
	private String selectedApprovalGroup;
	
	private List<Message> approverGroupList;
	
	//SAP
	
	private List<SapMessage> sapReturnMessage;
	
	
	//Documentum + SAP
	private String selectedThresholdAmount; 
		
	//SAP
	
	private Collection<BankingInformation> bankingInformation;
		
	//SAP
	
//	private ArrayList<SapMessage> lisOfSapMessages;
	
	private List<InvoiceLine> invoiceLines ;
	
	private PurchaseOrder purchaseOrder;
	
	private Supplier supplierDetail;
	
	//SAP
	
	private BlockingCodeEnumList blockingCodeList = new BlockingCodeEnumList();
	

	
	private String sapDocumentId;
	private String invoiceType;
	private String invoiceDate;
	private String companyVatNumber;
	private String companyTaxNumber;
	private String invoiceCurrency;
	private String invoiceNetAmount;
	private String invoiceVatAmount;
	private String invoiceGrossAmount;
	private String invoiceReference;
	private String salesOrderNumber;
	private Double freightCosts;
	private Double packagingCosts;
	private List<String> goodReceiptNumber;
	private String scanningReference;
	private String scanningDate;
	private String sapFIDocumentNumber;
	private int sapFIDocumentDate;
	private String sapMMDocumentNumber;
	private int sapMMDocumentDate;
	private String companyCode = "011";
	private int invoiceFamily;
	private int invoiceCategory;
	//Removed due to documentum requirement
	//private List<String> blockingCode;
	private String blockingCodeV;
	private String blockingCodeT;
	private int invoiceStatus;
	
	private String salesOrderPosition;
	private String sapInvoiceCreator;
	private String selectedIban;
	private String invoicecountryOrigin;
	private String paymentCondition;
	
	private String lifeCycle;
	
	private File content;
}
