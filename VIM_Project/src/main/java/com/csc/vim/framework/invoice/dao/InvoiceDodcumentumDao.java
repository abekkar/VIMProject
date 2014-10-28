package com.csc.vim.framework.invoice.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csc.vim.framework.common.dao.DctmDao;
import com.csc.vim.framework.invoice.model.Invoice;
import com.csc.vim.framework.properties.DctmModelType;
import com.csc.vim.framework.util.DateUtils;
import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.IDfList;
import com.documentum.operations.IDfFile;
import com.documentum.operations.IDfImportNode;
import com.documentum.operations.IDfImportOperation;
import com.documentum.operations.IDfOperationError;

@Repository
public class InvoiceDodcumentumDao extends DctmDao<Invoice> implements IInvoiceDao {

	@Autowired
	private com.csc.vim.framework.dao.impl.InvoiceDctmDao abeInvoiceDao;
	
	@Autowired
	private DateUtils dateUtils;
	
	protected org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(InvoiceDodcumentumDao.class);
	
	private static final String INVOICE_TYPE            = "invoice_type";
	private static final String INVOICE_CATEGORY        = "invoice_category";
	private static final String FIRST_LEVEL_CONTROLLER  = "first_level_controller";
	private static final String GLOBAL_LEVEL_CONTROLLER = "global_level_controller"; 
	private static final String SELECTED_APPROVAL_GROUP = "selected_approval_group";
	private static final String INVOICE_CURRENCY        = "invoice_currency";
	private static final String INVOICE_DATE            = "invoice_date";
	private static final String COMPANY_CODE            = "company_code";
	private static final String INVOICE_FAMILY          = "invoice_family";
	private static final String INVOICE_GROSS_AMOUNT    = "invoice_gross_amount";
	private static final String INVOICE_NET_AMOUNT      = "invoice_net_amount";
	private static final String INVOICE_NET_AMOUNT_EUR  = "invoice_net_amount_eur";
	private static final String INVOICE_VAT_AMOUNT      = "invoice_vat_amount";
	private static final String INVOICE_REFERENCE       = "invoice_reference";
	private static final String INVOICE_STATUS          = "invoice_status";
	private static final String BLOCKING_CODE_SAP       = "blocking_code_sap";
	private static final String BLOCKING_CODE_T         = "blocking_code_t";
	private static final String BLOCKING_CODE_V         = "blocking_code_v";
	private static final String INVOICE_LCT             = "invoice_lct";
	private static final String INVOICE_UCT             = "invoice_uct";
	private static final String INVOICE_COUNTRY_ORIGIN  = "invoice_country_origin";
	private static final String SAP_FI_DOC_NUM          = "sap_fi_document_number";
	private static final String SAP_MM_DOC_NUM          = "sap_mm_document_number";
	private static final String SAP_FI_DOC_DATE         = "sap_fi_document_date";
	private static final String SAP_MM_DOC_DATE         = "sap_mm_document_date";
	private static final String FREIGHT_COST            = "freight_cost";
	private static final String PACKAGING_COST          = "packaging_cost";
	private static final String PAYMENT_CONDITION       = "payment_condition";
	private static final String SEL_THRESHOLD_AMOUNT    = "selected_threshold_amount";
//	private static final String SELECTED_IBAN           = "selected_iban";
	private static final String SCANNING_REFERENCE      = "scanning_reference";
	private static final String SCANNING_DATE           = "scanning_date";
	private static final String COMPANY_TAX_NUMBER      = "company_tax_number";
	private static final String COMPANU_VAT_NUMBER      = "company_vat_number";
	private static final String SAP_INVOICE_CREATOR     = "sap_invoice_creator";
	private static final String SALES_ORDER_NUMBER      = "sales_order_number";
	private static final String SALES_ORDER_POSITION    = "sales_order_position";
	private static final String GOOD_RECEIPTS           = "good_receipts";
	private static final String INVOICE_IBAN_LIST       ="invoice_iban";
	
	/**
	 * Import a PDF Invoice file into Documentum
	 * Note that the Invoice contains its folder (see DematObject)
	 * @param Invoice 	pInvoiceContent	The invocie to be created. This contains has a property: content that contains the path of the file to be uploaded
	 * @param String 	pParentId 		FolderName where the document will be stored
	 * @author syongwaiman2
	 * @since 1.0
	 * @return r_object_id of the file that was freshly imported into DCTM
	 * @throws DfException 
	 *
	 */
	@Override
	public Invoice create(Invoice pInvoice) throws DfException {
		String pJavaFilename = pInvoice.getContent().getAbsolutePath();	// FullPath/name of the JAVA file to be uploaded to DCTM
		String pDctmFileName = pInvoice.getScanningReference();			// Name of the file to be created		
		String pDestFldrPath = pInvoice.getDctmFolder();				// The DCTM Folder where to store the uploaded file	 
		String pFormat = DctmModelType.INVOICE_CONTENT_TYPE;			// Type of object to be created into DCTM
		
		String pdfDctmId = null;			// The r_object_id of the new Document

			// Create import operation
			IDfClientX clientX = new DfClientX();
			IDfImportOperation importOp = clientX.getImportOperation();
			importOp.setSession(mySession);

			// Search for the folderID where to store the file
			IDfFolder invoiceFolder = mySession.getFolderByPath(pInvoice.getDctmFolder());
			//IDfFolder invoiceFolder = mySession.getFolderBySpecification("0b0d8b6680005eca");
			
			// check if file to be uploaded, exists on the file system
			IDfFile localFile = clientX.getFile(pJavaFilename);
			if (localFile.exists() != false) {

				// Set the destination Folder
				importOp.setDestinationFolderId(invoiceFolder.getObjectId());
				
				// add the file to the operation
				IDfImportNode impNode = (IDfImportNode) importOp.add(localFile);
				impNode.setFormat(pFormat);
				impNode.setNewObjectName(pDctmFileName);
				impNode.setDocbaseObjectType(DctmModelType.INVOICE);

				// Execute the import
				if (importOp.execute()) {

					// Get the new DCTM object
					IDfList myNodes = importOp.getNodes();
					IDfImportNode aNode = (IDfImportNode) myNodes.get(0);
					pdfDctmId = aNode.getNewObjectId().toString();
					
					// Update the POJO invoice with his new r_object_id
					pInvoice.setrObjectId(pdfDctmId);
					
					
					// Set the properties of the object
					IDfSysObject invoiceDctm = impNode.getNewObject();
					invoiceDctm.setString("object_name", pInvoice.getScanningReference());
					
					
					
					invoiceDctm.save();
				} else {
					IDfList errList = importOp.getErrors();
					if (errList.getCount() > 0) {
						for (int i = 0; i < errList.getCount(); i++) {
							IDfOperationError error = (IDfOperationError) errList.get(i);
							logger.error(error.getMessage());
						}
					}
					
					return null;
				}
			} else {
				System.out.println("File to upload does'nt exist");
			}
		

		return pInvoice;
	}

	@Override
	public Invoice getById(String pId) throws DfException {
		
		Invoice pInvoice = new Invoice();
		
		IDfSysObject invoice = null;
		invoice = (IDfSysObject) mySession.getObject(new DfId(pId));
		// Getting invoice properties
		pInvoice.setInvoiceType(invoice.getString(INVOICE_TYPE));
		if (null != invoice.getString(INVOICE_CATEGORY) && "" != invoice.getString(INVOICE_CATEGORY))
			pInvoice.setInvoiceCategory(Integer.parseInt(invoice.getString(INVOICE_CATEGORY)));
		pInvoice.setFirstLevelController(invoice.getString(FIRST_LEVEL_CONTROLLER));
		pInvoice.setGlobalLevelController(invoice.getString(GLOBAL_LEVEL_CONTROLLER));
		pInvoice.setSelectedApprovalGroup(invoice.getString(SELECTED_APPROVAL_GROUP));
		pInvoice.setInvoiceCurrency(invoice.getString(INVOICE_CURRENCY));
			pInvoice.setInvoiceDate(dateUtils.extractDate(invoice.getString(INVOICE_DATE)));
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
		pInvoice.setScanningReference(invoice.getString(SCANNING_REFERENCE));
			pInvoice.setScanningDate(dateUtils.extractDate(invoice.getString(SCANNING_DATE)));
		pInvoice.setCompanyTaxNumber(invoice.getString(COMPANY_TAX_NUMBER));
		pInvoice.setCompanyVatNumber(invoice.getString(COMPANU_VAT_NUMBER));
		pInvoice.setSapInvoiceCreator(invoice.getString(SAP_INVOICE_CREATOR));
		pInvoice.setSalesOrderNumber(invoice.getString(SALES_ORDER_NUMBER));
		pInvoice.setSalesOrderPosition(invoice.getString(SALES_ORDER_POSITION));
		if (pInvoice.getInvoiceIban()!= null )
		{
			for (int i = 0; i < invoice.getValueCount(INVOICE_IBAN_LIST); i++) {
				pInvoice.getInvoiceIban().add(invoice.getAllRepeatingStrings(INVOICE_IBAN_LIST, ","));
			}
		}
		else
		{
			pInvoice.setInvoiceIban(new ArrayList<String>());
			for (int i = 0; i < invoice.getValueCount(INVOICE_IBAN_LIST); i++) {
				pInvoice.getInvoiceIban().add(invoice.getAllRepeatingStrings(INVOICE_IBAN_LIST, ","));
			}
		}
		StringTokenizer listOfGoodReceipts = new StringTokenizer(invoice.getAllRepeatingStrings(GOOD_RECEIPTS, ","), ",");
		if (null != listOfGoodReceipts) {
			pInvoice.setGoodReceiptNumber(new ArrayList<String>());
			while (listOfGoodReceipts.hasMoreTokens())
				while (listOfGoodReceipts.hasMoreTokens()) {
					pInvoice.getGoodReceiptNumber().add(
							listOfGoodReceipts.nextToken());
				}
		}
		return pInvoice;
	}

	@Override
	public Collection<Invoice> getChildrenOfInvoice(String pParentId)
			throws DfException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String linkPurchaseOrder(String pInvoiceId, String poType, String pPoId) {
		return dctmHelper.createDmRelation(pInvoiceId,DctmModelType.INVOICE, pPoId, DctmModelType.PURCHASEORDER, DctmModelType.RL_INVOICE_PO);
	}

	@Override
	public String linkSupplier(String pInvoiceId, String pSupplierId) {
		return dctmHelper.createDmRelation(pInvoiceId,DctmModelType.INVOICE, pSupplierId, DctmModelType.SUPPLIER, DctmModelType.RL_INVOICE_SUPPLIER);
	}
	
	@Override
	public void linkBankingInformation(String pInvoiceId, Collection<String> pBankingInformation) {
		Iterator<String> it = pBankingInformation.iterator();
		while (it.hasNext()) {
			String id = it.next();
			dctmHelper.createDmRelation(pInvoiceId,DctmModelType.INVOICE, id, DctmModelType.BANKINFO, DctmModelType.RL_INVOICE_BANKINFO);

		}
	}

	@Override
	protected void updateJavaBeanWithDctmBean(Invoice pJavaBean,
			IDfPersistentObject pDctmBean) {
		
		
		
		
		
	}

	@Override
	protected void updateDctmBeanWithJavaBean(IDfPersistentObject pDctmBean,
			Invoice pJavaBean) {

	}


	
	
}
