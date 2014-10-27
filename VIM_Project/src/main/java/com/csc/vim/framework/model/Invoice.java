package com.csc.vim.framework.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.csc.vim.framework.properties.BlockingCodeEnumList;
import com.csc.vim.framework.util.SapMessage;


@XmlRootElement(name = "invoice")
@XmlAccessorType(XmlAccessType.FIELD)
public class Invoice {
	
	private List<Threshold> listOfThreshold;
	
	private String rObjectId;
	
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
	
	private List<BankDetails> listOfBankDetails;
		
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
	private double freightCosts;
	private double packagingCosts;
	private boolean goodReceiptNumber;
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
	
	
	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}

	public String getSapDocumentId() {
		return sapDocumentId;
	}
	
	public void setSapDocumentId(String sapDocumentId) {
		this.sapDocumentId = sapDocumentId;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}
	
	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public String getrObjectId() {
		return rObjectId;
	}
	
	public void setrObjectId(String rObjectId) {
		this.rObjectId = rObjectId;
	}
	
	public List<SapMessage> getSapReturnMessage() {
		return sapReturnMessage;
	}
	
	public void setSapReturnMessage(List<SapMessage> sapReturnMessage) {
		this.sapReturnMessage = sapReturnMessage;
	}


	public List<InvoiceLine> getInvoiceLines() {
		return invoiceLines;
	}
	
	public void setInvoiceLines(List<InvoiceLine> invoiceLines) {
		this.invoiceLines = invoiceLines;
	}

	public Supplier getSupplierDetail() {
		return supplierDetail;
	}
	
	public void setSupplierDetail(Supplier supplierDetail) {
		this.supplierDetail = supplierDetail;
	}

	public String getInvoiceType() {
		return invoiceType;
	}
	
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}
	
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceNetAmount() {
		return invoiceNetAmount;
	}
	
	public void setInvoiceNetAmount(String invoiceNetAmount) {
		this.invoiceNetAmount = invoiceNetAmount;
	}

	public String getInvoiceVatAmount() {
		return invoiceVatAmount;
	}
	
	public void setInvoiceVatAmount(String invoiceVatAmount) {
		this.invoiceVatAmount = invoiceVatAmount;
	}

	public String getInvoiceGrossAmount() {
		return invoiceGrossAmount;
	}
	
	public void setInvoiceGrossAmount(String invoiceGrossAmount) {
		this.invoiceGrossAmount = invoiceGrossAmount;
	}

	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}
	
	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}



	public boolean isGoodReceiptNumber() {
		return goodReceiptNumber;
	}

	public void setGoodReceiptNumber(boolean goodReceiptNumber) {
		this.goodReceiptNumber = goodReceiptNumber;
	}

	public String getCompanyCode() {
		return companyCode;
	}
	
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public int getInvoiceCategory() {
		return invoiceCategory;
	}
	
	public void setInvoiceCategory(int invoiceCategory) {
		this.invoiceCategory = invoiceCategory;
	}

	public int getInvoiceStatus() {
		return invoiceStatus;
	}
	
	public void setInvoiceStatus(int invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getSalesOrderPosition() {
		return salesOrderPosition;
	}
	
	public void setSalesOrderPosition(String salesOrderPosition) {
		this.salesOrderPosition = salesOrderPosition;
	}

	public String getSapInvoiceCreator() {
		return sapInvoiceCreator;
	}
	
	public void setSapInvoiceCreator(String sapInvoiceCreator) {
		this.sapInvoiceCreator = sapInvoiceCreator;
	}

	public String getSelectedIban() {
		return selectedIban;
	}
	
	public void setSelectedIban(String selectedIban) {
		this.selectedIban = selectedIban;
	}

	public String getPaymentCondition() {
		return paymentCondition;
	}
	
	public void setPaymentCondition(String paymentCondition) {
		this.paymentCondition = paymentCondition;
	}

	public BlockingCodeEnumList getBlockingCodeList() {
		return blockingCodeList;
	}
	
	public void setBlockingCodeList(BlockingCodeEnumList blockingCodeList) {
		this.blockingCodeList = blockingCodeList;
	}

	public int getInvoiceFamily() {
		return invoiceFamily;
	}
	
	public void setInvoiceFamily(int invoiceFamily) {
		this.invoiceFamily = invoiceFamily;
	}

	public String getInvoiceCurrency() {
		return invoiceCurrency;
	}
	
	public void setInvoiceCurrency(String invoiceCurrency) {
		this.invoiceCurrency = invoiceCurrency;
	}

	public String getInvoiceReference() {
		return invoiceReference;
	}
	
	public void setInvoiceReference(String invoiceReference) {
		this.invoiceReference = invoiceReference;
	}


	
	public void setListOfBankDetails(ArrayList<BankDetails> listOfBankDetails) {
		this.listOfBankDetails = listOfBankDetails;
	}

//	public ArrayList<SapMessage> getLisOfSapMessages() {
//		return lisOfSapMessages;
//	}
//	
//	public void setLisOfSapMessages(ArrayList<SapMessage> lisOfSapMessages) {
//		this.lisOfSapMessages = lisOfSapMessages;
//	}
	
	
	public List<BankDetails> getListOfBankDetails() {
		return listOfBankDetails;
	}
	
	public void setListOfBankDetails(List<BankDetails> listOfBankDetails) {
		this.listOfBankDetails = listOfBankDetails;
	}

	public List<Message> getProcessorDecision() {
		return processorDecision;
	}
	
	public void setProcessorDecision(List<Message> processorDecision) {
		this.processorDecision = processorDecision;
	}

	public String getFirstLevelController() {
		return firstLevelController;
	}
	
	public void setFirstLevelController(String firstLevelController) {
		this.firstLevelController = firstLevelController;
	}

	public String getGlobalLevelController() {
		return GlobalLevelController;
	}
	
	public void setGlobalLevelController(String globalLevelController) {
		GlobalLevelController = globalLevelController;
	}

	public String getSelectedApprovalGroup() {
		return selectedApprovalGroup;
	}
	
	public void setSelectedApprovalGroup(String selectedApprovalGroup) {
		this.selectedApprovalGroup = selectedApprovalGroup;
	}

	public List<Message> getApproverGroupList() {
		return approverGroupList;
	}
	
	public void setApproverGroupList(List<Message> approverGroupList) {
		this.approverGroupList = approverGroupList;
	}

	public Invoice() {
		super();
	}

	public String getSelectedThresholdAmount() {
		return selectedThresholdAmount;
	}
	
	public void setSelectedThresholdAmount(String selectedThresholdAmount) {
		this.selectedThresholdAmount = selectedThresholdAmount;
	}

	public String getCompanyVatNumber() {
		return companyVatNumber;
	}
	
	public void setCompanyVatNumber(String companyVatNumber) {
		this.companyVatNumber = companyVatNumber;
	}

	public String getCompanyTaxNumber() {
		return companyTaxNumber;
	}
	
	public void setCompanyTaxNumber(String companyTaxNumber) {
		this.companyTaxNumber = companyTaxNumber;
	}

	public double getFreightCosts() {
		return freightCosts;
	}
	
	public void setFreightCosts(double freightCosts) {
		this.freightCosts = freightCosts;
	}

	public double getPackagingCosts() {
		return packagingCosts;
	}
	
	public void setPackagingCosts(double packagingCosts) {
		this.packagingCosts = packagingCosts;
	}

	public String getScanningReference() {
		return scanningReference;
	}
	
	public void setScanningReference(String scanningReference) {
		this.scanningReference = scanningReference;
	}

	public String getScanningDate() {
		return scanningDate;
	}
	
	public void setScanningDate(String scanningDate) {
		this.scanningDate = scanningDate;
	}

	public String getSapFIDocumentNumber() {
		return sapFIDocumentNumber;
	}
	
	public void setSapFIDocumentNumber(String sapFIDocumentNumber) {
		this.sapFIDocumentNumber = sapFIDocumentNumber;
	}

	public int getSapFIDocumentDate() {
		return sapFIDocumentDate;
	}
	
	public void setSapFIDocumentDate(int sapFIDocumentDate) {
		this.sapFIDocumentDate = sapFIDocumentDate;
	}

	public String getSapMMDocumentNumber() {
		return sapMMDocumentNumber;
	}
	
	public void setSapMMDocumentNumber(String sapMMDocumentNumber) {
		this.sapMMDocumentNumber = sapMMDocumentNumber;
	}

	public int getSapMMDocumentDate() {
		return sapMMDocumentDate;
	}
	
	public void setSapMMDocumentDate(int sapMMDocumentDate) {
		this.sapMMDocumentDate = sapMMDocumentDate;
	}

	public String getInvoicecountryOrigin() {
		return invoicecountryOrigin;
	}

	
	public void setInvoicecountryOrigin(String invoicecountryOrigin) {
		this.invoicecountryOrigin = invoicecountryOrigin;
	}

	public String getSapBlockingCode() {
		return sapBlockingCode;
	}
	
	public void setSapBlockingCode(String sapBlockingCode) {
		this.sapBlockingCode = sapBlockingCode;
	}

	public Double getInvoiceNetAmountEur() {
		return invoiceNetAmountEur;
	}
	
	public void setInvoiceNetAmountEur(Double invoiceNetAmountEur) {
		this.invoiceNetAmountEur = invoiceNetAmountEur;
	}

	public int getInvoiceNetAmountThreshold() {
		return invoiceNetAmountThreshold;
	}
	
	public void setInvoiceNetAmountThreshold(int invoiceNetAmountThreshold) {
		this.invoiceNetAmountThreshold = invoiceNetAmountThreshold;
	}

	public Double getInvoiceUCT() {
		return invoiceUCT;
	}
	
	public void setInvoiceUCT(Double invoiceUCT) {
		this.invoiceUCT = invoiceUCT;
	}

	public Double getInvoiceLCT() {
		return invoiceLCT;
	}
	
	public void setInvoiceLCT(Double invoiceLCT) {
		this.invoiceLCT = invoiceLCT;
	}

	public String getBlockingCodeV() {
		return blockingCodeV;
	}
	
	public void setBlockingCodeV(String blockingCodeV) {
		this.blockingCodeV = blockingCodeV;
	}

	public String getBlockingCodeT() {
		return blockingCodeT;
	}
	
	public void setBlockingCodeT(String blockingCodeT) {
		this.blockingCodeT = blockingCodeT;
	}

	public List<Threshold> getListOfThreshold() {
		return listOfThreshold;
	}
	
	public void setListOfThreshold(List<Threshold> listOfThreshold) {
		this.listOfThreshold = listOfThreshold;
	}

	public List<String> getInvoiceIban() {
		return invoiceIban;
	}

	public void setInvoiceIban(List<String> invoiceIban) {
		this.invoiceIban = invoiceIban;
	}

	public Invoice(List<Threshold> listOfThreshold, String rObjectId,
			List<String> message, String sapBlockingCode,
			Double invoiceNetAmountEur, int invoiceNetAmountThreshold,
			Double invoiceUCT, Double invoiceLCT, List<String> invoiceIban,
			List<Message> processorDecision, String firstLevelController,
			String globalLevelController, String selectedApprovalGroup,
			List<Message> approverGroupList, List<SapMessage> sapReturnMessage,
			String selectedThresholdAmount,
			List<BankDetails> listOfBankDetails,
			List<InvoiceLine> invoiceLines, PurchaseOrder purchaseOrder,
			Supplier supplierDetail, BlockingCodeEnumList blockingCodeList,
			String sapDocumentId, String invoiceType, String invoiceDate,
			String companyVatNumber, String companyTaxNumber,
			String invoiceCurrency, String invoiceNetAmount,
			String invoiceVatAmount, String invoiceGrossAmount,
			String invoiceReference, String salesOrderNumber,
			double freightCosts, double packagingCosts,
			boolean goodReceiptNumber, String scanningReference,
			String scanningDate, String sapFIDocumentNumber,
			int sapFIDocumentDate, String sapMMDocumentNumber,
			int sapMMDocumentDate, String companyCode, int invoiceFamily,
			int invoiceCategory, String blockingCodeV, String blockingCodeT,
			int invoiceStatus, String salesOrderPosition,
			String sapInvoiceCreator, String selectedIban,
			String invoicecountryOrigin, String paymentCondition) {
		super();
		this.listOfThreshold = listOfThreshold;
		this.rObjectId = rObjectId;
		this.message = message;
		this.sapBlockingCode = sapBlockingCode;
		this.invoiceNetAmountEur = invoiceNetAmountEur;
		this.invoiceNetAmountThreshold = invoiceNetAmountThreshold;
		this.invoiceUCT = invoiceUCT;
		this.invoiceLCT = invoiceLCT;
		this.invoiceIban = invoiceIban;
		this.processorDecision = processorDecision;
		this.firstLevelController = firstLevelController;
		GlobalLevelController = globalLevelController;
		this.selectedApprovalGroup = selectedApprovalGroup;
		this.approverGroupList = approverGroupList;
		this.sapReturnMessage = sapReturnMessage;
		this.selectedThresholdAmount = selectedThresholdAmount;
		this.listOfBankDetails = listOfBankDetails;
		this.invoiceLines = invoiceLines;
		this.purchaseOrder = purchaseOrder;
		this.supplierDetail = supplierDetail;
		this.blockingCodeList = blockingCodeList;
		this.sapDocumentId = sapDocumentId;
		this.invoiceType = invoiceType;
		this.invoiceDate = invoiceDate;
		this.companyVatNumber = companyVatNumber;
		this.companyTaxNumber = companyTaxNumber;
		this.invoiceCurrency = invoiceCurrency;
		this.invoiceNetAmount = invoiceNetAmount;
		this.invoiceVatAmount = invoiceVatAmount;
		this.invoiceGrossAmount = invoiceGrossAmount;
		this.invoiceReference = invoiceReference;
		this.salesOrderNumber = salesOrderNumber;
		this.freightCosts = freightCosts;
		this.packagingCosts = packagingCosts;
		this.goodReceiptNumber = goodReceiptNumber;
		this.scanningReference = scanningReference;
		this.scanningDate = scanningDate;
		this.sapFIDocumentNumber = sapFIDocumentNumber;
		this.sapFIDocumentDate = sapFIDocumentDate;
		this.sapMMDocumentNumber = sapMMDocumentNumber;
		this.sapMMDocumentDate = sapMMDocumentDate;
		this.companyCode = companyCode;
		this.invoiceFamily = invoiceFamily;
		this.invoiceCategory = invoiceCategory;
		this.blockingCodeV = blockingCodeV;
		this.blockingCodeT = blockingCodeT;
		this.invoiceStatus = invoiceStatus;
		this.salesOrderPosition = salesOrderPosition;
		this.sapInvoiceCreator = sapInvoiceCreator;
		this.selectedIban = selectedIban;
		this.invoicecountryOrigin = invoicecountryOrigin;
		this.paymentCondition = paymentCondition;
	}

	

	
}
