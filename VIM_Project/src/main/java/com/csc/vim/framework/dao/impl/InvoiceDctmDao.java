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
	// private static String DQL_QUERY_RETRIEVE_INVOICE =
	// "select * from invoice where r_object_id='";
	private static String NAMESPACE = "demat";
	private static String DQL_QUERY_RETRIEVE_INVOICE_LINE = "select * from "+NAMESPACE+"_invoice_line,"+NAMESPACE+"_invoice where "+NAMESPACE+"_invoice.r_object_id='";
	private static String DQL_QUERY_RETRIEVE_INVOICE_SUPPLIER = "select * from supplier,"+NAMESPACE+"_invoice where "+NAMESPACE+"_invoice.r_object_id='";
	private static String DQL_QUERY_RETRIEVE_INVOICE_PO = "select * from purchase_order,"+NAMESPACE+"_invoice where "+NAMESPACE+"_invoice.r_object_id='";
	private static String DQL_QUERY_RETRIEVE_BANK_DETAIL = "select * from bank_detail,"+NAMESPACE+"_invoice where "+NAMESPACE+"_invoice.r_object_id='";
	private static String DQL_QUERY_RETRIEVE_APPROVER = "select * from message,"+NAMESPACE+"_invoice where message.role='APPROVER' and "+NAMESPACE+"_invoice.r_object_id='";
	private static String DQL_QUERY_RETRIEVE_PROCESSOR = "select * from message,"+NAMESPACE+"_invoice where message.role='PROCESSOR' and "+NAMESPACE+"_invoice.r_object_id='";
	private static String DQL_QUERY_RETRIEVE_INVOICE_BY_STATUS = "select * from "+NAMESPACE+"_invoice where invoice_status='";

	/**
	 * See the IInvoiceDao Interface to get informations about every method
	 * 
	 * @author abekkar
	 * @since 1.0
	 */
	public Invoice read(Invoice pInvoice) throws JCoException, IOException {
		// TODO
		// Optimize DQL request (fusion des requetes en une)
		try {
			IDfSession session = dctmInstance.getSession();
			IDfSysObject invoice = (IDfSysObject) session.getObject(new DfId(
					pInvoice.getrObjectId()));
			// Getting invoice properties
			pInvoice.setInvoiceType(invoice.getString("invoice_type"));
			if (null != invoice.getString("invoice_category") && "" != invoice.getString("invoice_category"))
				pInvoice.setInvoiceCategory(Integer.parseInt(invoice
						.getString("invoice_category")));
			pInvoice.setFirstLevelController(invoice
					.getString("first_level_controller"));
//			pInvoice.setGlobalLevelController(invoice
//					.getString("global_level_controller"));
			pInvoice.setSelectedApprovalGroup(invoice
					.getString("selected_approval_group"));
			pInvoice.setInvoiceCurrency(invoice.getString("invoice_currency"));
			pInvoice.setInvoiceDate(invoice.getString("invoice_date"));
			pInvoice.setCompanyCode(invoice.getString("company_code"));
			if (null != invoice.getString("invoice_family"))
				pInvoice.setInvoiceFamily(Integer.parseInt(invoice
						.getString("invoice_family")));
			pInvoice.setInvoiceGrossAmount(invoice
					.getString("invoice_gross_amount"));
			pInvoice.setInvoiceNetAmount(invoice
					.getString("invoice_net_amount"));
			pInvoice.setInvoiceVatAmount(invoice
					.getString("invoice_vat_amount"));
			pInvoice.setInvoiceGrossAmount(invoice
					.getString("invoice_gross_amount"));
			pInvoice.setInvoiceReference(invoice.getString("invoice_reference"));
			if (null != invoice.getString("invoice_status"))
				pInvoice.setInvoiceStatus(Integer.parseInt(invoice
						.getString("invoice_status")));
			pInvoice.setInvoiceType(invoice.getString("invoice_type"));
			pInvoice.setInvoiceVatAmount(invoice
					.getString("invoice_vat_amount"));
			pInvoice.setSapBlockingCode(invoice
					.getString("blocking_code_sap"));
			pInvoice.setBlockingCodeT(invoice.getString("blocking_code_t"));
			pInvoice.setBlockingCodeV(invoice.getString("blocking_code_v"));
			pInvoice.setInvoiceLCT(Double.parseDouble(invoice.getString("invoice_lct")));
			pInvoice.setInvoiceUCT(Double.parseDouble(invoice.getString("invoice_uct")));
			pInvoice.setInvoicecountryOrigin(invoice
					.getString("invoice_country_origin"));
			pInvoice.setSapFIDocumentNumber(invoice
					.getString("sap_fi_document_number"));
			pInvoice.setSapMMDocumentNumber(invoice
					.getString("sap_mm_document_number"));
			if (null != invoice.getString("sap_fi_document_date"))
				pInvoice.setSapFIDocumentDate(Integer.parseInt(invoice
						.getString("sap_fi_document_date")));
			if (null != invoice.getString("sap_mm_document_date"))
				pInvoice.setSapMMDocumentDate(Integer.parseInt(invoice
						.getString("sap_mm_document_date")));
			pInvoice.setFreightCosts(Double.parseDouble(invoice
					.getString("freight_cost")));
			pInvoice.setInvoiceDate(invoice.getString("invoice_date"));
			pInvoice.setPackagingCosts(Double.parseDouble(invoice
					.getString("packaging_cost")));
			pInvoice.setPaymentCondition(invoice.getString("payment_condition"));
			pInvoice.setSelectedThresholdAmount(invoice
					.getString("selected_threshold_amount"));
			pInvoice.setSelectedIban(invoice.getString("selected_iban"));
			pInvoice.setScanningReference(invoice
					.getString("scanning_reference"));
			pInvoice.setScanningDate(invoice.getString("scanning_date"));
			pInvoice.setCompanyTaxNumber(invoice
					.getString("company_tax_number"));
			pInvoice.setCompanyVatNumber(invoice
					.getString("campany_vat_number"));
			pInvoice.setSapInvoiceCreator(invoice
					.getString("sap_invoice_creator"));
			pInvoice.setSalesOrderNumber(invoice
					.getString("sales_order_number"));
			pInvoice.setSalesOrderPosition(invoice
					.getString("sales_order_position"));
			StringTokenizer listOfGoodReceipts = new StringTokenizer(
					invoice.getAllRepeatingStrings("good_receipts", ","), ",");
			if (null != listOfGoodReceipts) {
				pInvoice.setGoodReceiptNumber(new ArrayList<String>());
				while (listOfGoodReceipts.hasMoreTokens())
					while (listOfGoodReceipts.hasMoreTokens()) {
						pInvoice.getGoodReceiptNumber().add(
								listOfGoodReceipts.nextToken());
					}
			}
			// Populate invoiceLines
			pInvoice.setInvoiceLines(new ArrayList<InvoiceLine>());
			IDfQuery queryGetInvoiceLine = new DfQuery();
			queryGetInvoiceLine.setDQL(DQL_QUERY_RETRIEVE_INVOICE_LINE
					+ pInvoice.getrObjectId() + "'");
			IDfCollection invoiceLineCol = queryGetInvoiceLine.execute(session,
					IDfQuery.DF_READ_QUERY);
			if (null != invoiceLineCol) {
				while (invoiceLineCol.next()) {
					InvoiceLine invoiceLine = new InvoiceLine();
					invoiceLine.setVendorAssignmentNumber(invoiceLineCol
							.getString("vendor_assigment_number"));
					invoiceLine.setCostCenter(invoiceLineCol
							.getString("cost_center"));
					invoiceLine.setGlItemText(invoiceLineCol
							.getString("gl_item_text"));
					invoiceLine.setGlAccount(invoiceLineCol
							.getString("gl_account"));
					invoiceLine.setGlAccountNumber(invoiceLineCol
							.getString("gl_account_number"));
					invoiceLine.setInternalOrder(invoiceLineCol
							.getString("internal_order"));
					invoiceLine.setPartnerProfitCenter(invoiceLineCol
							.getString("partner_profit_center"));
					invoiceLine.setPlantNumber(invoiceLineCol
							.getString("plant_number"));
					invoiceLine.setProfitCenter(invoiceLineCol
							.getString("profit_center"));
					invoiceLine.setQuantity(Integer.parseInt(invoiceLineCol
							.getString("quantity")));
					invoiceLine.setSalesOrder(invoiceLineCol
							.getString("sales_order"));
					invoiceLine.setVendorItemNumber(invoiceLineCol
							.getString("vendor_item_number"));
					invoiceLine.setWbs(invoiceLineCol.getString("wbs"));

					invoiceLine.setBaseLineDate(dateUtils.stringToDate(
							invoiceLineCol.getString("base_line_date"),
							"dd/mm/yyyy"));
					invoiceLine.setTaxRate(Integer.parseInt(invoiceLineCol
							.getString("tax_rate")));
					invoiceLine.setTaxItemNumber(Integer
							.parseInt(invoiceLineCol
									.getString("tax_item_number")));
					invoiceLine.setGlTaxAccount(invoiceLineCol
							.getString("tax_gl_account"));
					invoiceLine.setMaterial(invoiceLineCol
							.getString("material"));
					invoiceLine.setSalestaxCode(invoiceLineCol
							.getString("sales_tax_order"));
					invoiceLine.setSalestaxCode(invoiceLineCol
							.getString("gl_sales_tax_code"));
					pInvoice.getInvoiceLines().add(invoiceLine);
				}
			}
			// Populate supplier detail
			IDfQuery queryGetInvoiceSupplier = new DfQuery();
			queryGetInvoiceSupplier.setDQL(DQL_QUERY_RETRIEVE_INVOICE_SUPPLIER
					+ pInvoice.getrObjectId() + "'");
			IDfCollection invoiceSupplierDetail = queryGetInvoiceSupplier
					.execute(session, IDfQuery.DF_READ_QUERY);
			if (null != invoiceSupplierDetail) {
				while (invoiceSupplierDetail.next()) {
					Supplier supplier = new Supplier();
					supplier.setSupplierSelectedIban(invoiceLineCol
							.getString("selected_iban"));
					if (invoiceLineCol.getString("supplier_cpd") == "0")
						supplier.setSupplierCPD(false);
					else
						supplier.setSupplierCPD(true);
					supplier.setSupplierIndustry(invoiceLineCol
							.getString("supplier_industry"));
					supplier.setSupplierInvoiceAddress(invoiceLineCol
							.getString("supplier_invoice_address"));
					supplier.setSupplierInvoiceCity(invoiceLineCol
							.getString("supplier_invoice_city"));
					supplier.setSupplierInvoiceCountry(invoiceLineCol
							.getString("supplier_invoice_country"));
					supplier.setSupplierInvoiceEmail(invoiceLineCol
							.getString("supplier_invoice_email"));
					supplier.setSupplierInvoicePostCode(invoiceLineCol
							.getString("supplier_invoice_post_code"));
					supplier.setSupplierName(invoiceLineCol
							.getString("supplier_name"));
					supplier.setSupplierTaxNumber(invoiceLineCol
							.getString("supplier_tax_number"));
					supplier.setSupplierVatNumber(invoiceLineCol
							.getString("supplier_vat_number"));
					pInvoice.setSupplierDetail(supplier);
				}
			}

			// Populate Purchase Order Detail
			IDfQuery queryGetInvoicePO = new DfQuery();
			queryGetInvoicePO.setDQL(DQL_QUERY_RETRIEVE_INVOICE_PO
					+ pInvoice.getrObjectId() + "'");
			IDfCollection invoicePODetail = queryGetInvoicePO.execute(session,
					IDfQuery.DF_READ_QUERY);
			if (null != invoicePODetail) {
				while (invoicePODetail.next()) {
					PurchaseOrder purchaseOrder = new PurchaseOrder();
					purchaseOrder.setPoDocumentType(invoicePODetail
							.getString("document_type"));
					purchaseOrder.setPoNumber(invoicePODetail
							.getString("po_number"));
					purchaseOrder.setPoNumberPosition(invoicePODetail
							.getString("po_number_position"));
					purchaseOrder.setSupplierName(invoicePODetail
							.getString("vendor_name"));
					purchaseOrder.setSupplierNumber(invoicePODetail
							.getString("vendor_number"));
					pInvoice.setPurchaseOrder(purchaseOrder);
				}
			}

			// Populate Bank Detail
			IDfQuery queryGetBankDetail = new DfQuery();
			queryGetBankDetail.setDQL(DQL_QUERY_RETRIEVE_BANK_DETAIL
					+ pInvoice.getrObjectId() + "'");
			IDfCollection invoiceBankDetail = queryGetBankDetail.execute(
					session, IDfQuery.DF_READ_QUERY);
			if (null != invoiceBankDetail) {
				pInvoice.setListOfBankDetails(new ArrayList<BankDetails>());
				while (invoiceBankDetail.next()) {
					BankDetails bankDetail = new BankDetails();
					bankDetail.setBankName(invoiceBankDetail
							.getString("bank_name"));
					bankDetail.setAccountIban(invoiceBankDetail
							.getString("supplier_iban"));
					bankDetail.setAccountName(invoiceBankDetail
							.getString("supplier_name"));
					pInvoice.getListOfBankDetails().add(bankDetail);
				}
			}

			// Populate Approver list
			IDfQuery queryGetApprovers = new DfQuery();
			queryGetApprovers.setDQL(DQL_QUERY_RETRIEVE_APPROVER
					+ pInvoice.getrObjectId() + "'");
			IDfCollection invoiceApproverList = queryGetApprovers.execute(
					session, IDfQuery.DF_READ_QUERY);
			if (null != invoiceApproverList) {
				pInvoice.setApproverGroupList(new ArrayList<Message>());
				while (invoiceApproverList.next()) {
					Message message = new Message();
					message.setLogin(invoiceApproverList.getString("login"));
					message.setContentText(invoiceApproverList
							.getString("comment"));
					if (null != invoiceApproverList.getString("sequence"))
						message.setSequence(Integer
								.parseInt(invoiceApproverList
										.getString("sequence")));
					// TODO
					// verifier la bonne conversion de string vers date
					message.setDate(dateUtils.stringToDate(
							invoiceApproverList.getString("date"), "dd-mm-yyyy"));
					pInvoice.getApproverGroupList().add(message);
				}
			}

			// Populate Processor list
			IDfQuery queryGetProcessors = new DfQuery();
			queryGetProcessors.setDQL(DQL_QUERY_RETRIEVE_PROCESSOR
					+ pInvoice.getrObjectId() + "'");
			IDfCollection invoiceProcessorList = queryGetProcessors.execute(
					session, IDfQuery.DF_READ_QUERY);
			if (null != invoiceProcessorList) {
				pInvoice.setProcessorDecision(new ArrayList<Message>());
				while (invoiceProcessorList.next()) {
					Message message = new Message();
					message.setLogin(invoiceApproverList.getString("login"));
					message.setContentText(invoiceApproverList
							.getString("comment"));
					if (null != invoiceApproverList.getString("sequence"))
						message.setSequence(Integer
								.parseInt(invoiceApproverList
										.getString("sequence")));
					// TODO
					// verifier la bonne conversion de string vers date
					message.setDate(dateUtils.stringToDate(
							invoiceApproverList.getString("date"), "dd-mm-yyyy"));
					pInvoice.getProcessorDecision().add(message);
				}
			}
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
		StringBuilder invoiceDqlUpdater = new StringBuilder(
				"UPDATE invoice OBJECTS ");
		// Setting invoice properties
		invoiceDqlUpdater.append("SET invoice_type="
				+ pInvoice.getInvoiceType() + " ");
		invoiceDqlUpdater.append("SET invoice_category="
				+ pInvoice.getInvoiceCategory() + " ");
		invoiceDqlUpdater.append("SET first_level_group="
				+ pInvoice.getFirstLevelController() + " ");
		invoiceDqlUpdater.append("SET global_level_controller="
				+ pInvoice.getGlobalLevelController() + " ");
		invoiceDqlUpdater.append("SET selected_approval_group="
				+ pInvoice.getSelectedApprovalGroup() + " ");
		invoiceDqlUpdater.append("SET invoice_currency="
				+ pInvoice.getInvoiceCurrency() + " ");
		invoiceDqlUpdater.append("SET invoice_date="
				+ pInvoice.getInvoiceDate() + " ");
		invoiceDqlUpdater.append("SET company_code="
				+ pInvoice.getCompanyCode() + " ");
		invoiceDqlUpdater.append("SET invoice_family="
				+ pInvoice.getInvoiceFamily() + " ");
		invoiceDqlUpdater.append("SET invoice_gross_amount="
				+ pInvoice.getInvoiceGrossAmount() + " ");
		invoiceDqlUpdater.append("SET invoice_net_amount="
				+ pInvoice.getInvoiceNetAmount() + " ");
		invoiceDqlUpdater.append("SET invoice_reference="
				+ pInvoice.getInvoiceReference() + " ");
		invoiceDqlUpdater.append("SET invoice_status="
				+ pInvoice.getInvoiceStatus() + " ");
		invoiceDqlUpdater.append("SET invoice_vat_amount="
				+ pInvoice.getInvoiceVatAmount() + " ");
		invoiceDqlUpdater.append("SET invoice_country_origin="
				+ pInvoice.getInvoicecountryOrigin() + " ");
		invoiceDqlUpdater.append("SET fi_document_number="
				+ pInvoice.getSapFIDocumentNumber() + " ");
		invoiceDqlUpdater.append("SET mm_document_number="
				+ pInvoice.getSapMMDocumentNumber() + " ");
		// TODO
		// if (pInvoice.getFiDocumentDate() != 0 )
		invoiceDqlUpdater.append("SET fi_document_date="
				+ pInvoice.getSapFIDocumentDate() + " ");
		// if (null != pInvoice.getMmDocumentDate())
		invoiceDqlUpdater.append("SET mm_document_date="
				+ pInvoice.getSapMMDocumentDate() + " ");
		invoiceDqlUpdater.append("SET freight_cost="
				+ pInvoice.getFreightCosts() + " ");
		invoiceDqlUpdater.append("SET packaging_cost="
				+ pInvoice.getPackagingCosts() + " ");
		invoiceDqlUpdater.append("SET payment_condition="
				+ pInvoice.getPaymentCondition() + " ");
		invoiceDqlUpdater.append("SET selected_threshold_amount="
				+ pInvoice.getSelectedThresholdAmount() + " ");
		invoiceDqlUpdater.append("SET selected_iban="
				+ pInvoice.getSelectedIban() + " ");
		invoiceDqlUpdater.append("SET scanning_reference="
				+ pInvoice.getScanningReference() + " ");
		invoiceDqlUpdater.append("SET scanning_date="
				+ pInvoice.getScanningDate() + " ");
		invoiceDqlUpdater.append("SET company_tax_number="
				+ pInvoice.getCompanyTaxNumber() + " ");
		invoiceDqlUpdater.append("SET company_vat_number="
				+ pInvoice.getCompanyVatNumber() + " ");
		invoiceDqlUpdater.append("SET sap_invoice_creator="
				+ pInvoice.getSapInvoiceCreator() + " ");
		invoiceDqlUpdater.append("SET sales_order_number="
				+ pInvoice.getSalesOrderNumber() + " ");
		invoiceDqlUpdater.append("SET sales_order_position="
				+ pInvoice.getSalesOrderPosition() + " ");

		// StringTokenizer listOfBlokingCode=new
		// StringTokenizer(collection.getAllRepeatingStrings("blocking_code",","),",");
		// if (null != listOfBlokingCode){
		// pInvoice.setBlockingCode(new ArrayList<String>());
		// while (listOfBlokingCode.hasMoreTokens())
		// while(listOfBlokingCode.hasMoreTokens()){
		// pInvoice.getBlockingCode().add(listOfBlokingCode.nextToken());
		// }
		// }
		// StringTokenizer listOfGoodReceipts=new
		// StringTokenizer(collection.getAllRepeatingStrings("blocking_code",","),",");
		// if (null != listOfGoodReceipts){
		// pInvoice.setGoodReceiptNumber(new ArrayList<String>());
		// while (listOfGoodReceipts.hasMoreTokens())
		// while(listOfGoodReceipts.hasMoreTokens()){
		// pInvoice.getGoodReceiptNumber().add(listOfGoodReceipts.nextToken());
		// }
		// }
		invoiceDqlUpdater.append(" WHERE r_object_id = '"
				+ pInvoice.getrObjectId() + "' ");
		logger.debug("Executing: " + invoiceDqlUpdater.toString());
		DQLquery.setDQL(invoiceDqlUpdater.toString());
		DQLquery.execute(dctmInstance.getSession(), IDfQuery.DF_EXEC_QUERY);

		// Updating invoiceLines
		// Getting invoice lines
		// check if the invoice line exist then update else create the invoice
		// line
		boolean updatingLine = false;
		IDfQuery queryGetInvoiceLine = new DfQuery();
		queryGetInvoiceLine.setDQL(DQL_QUERY_RETRIEVE_INVOICE_LINE
				+ pInvoice.getrObjectId() + "'");
		IDfCollection invoiceLineCol = queryGetInvoiceLine.execute(session,
				IDfQuery.DF_READ_QUERY);
		if (null != invoiceLineCol) {
			while (invoiceLineCol.next()) {
				for (InvoiceLine invoiceLine : pInvoice.getInvoiceLines()) {
					if (invoiceLine.getrObjectId().equalsIgnoreCase(
							invoiceLineCol.getString("r_object_id"))) {
						StringBuilder invoiceLinesDqlUpdater = new StringBuilder(
								"UPDATE invoice_line OBJECTS ");
						invoiceLinesDqlUpdater.append("SET amount="
								+ invoiceLineCol.getString("amount") + " ");
						invoiceLinesDqlUpdater
								.append("SET assignement_vendor_number="
										+ invoiceLineCol
												.getString("assignement_vendor_number")
										+ " ");
						invoiceLinesDqlUpdater.append("SET base_line_date="
								+ invoiceLineCol.getString("base_line_date")
								+ " ");
						invoiceLinesDqlUpdater
								.append("SET cost_center="
										+ invoiceLineCol
												.getString("cost_center") + " ");
						invoiceLinesDqlUpdater.append("SET gl_item_text="
								+ invoiceLineCol.getString("gl_item_text")
								+ " ");
						invoiceLinesDqlUpdater.append("SET gl_account="
								+ invoiceLineCol.getString("gl_account") + " ");
						invoiceLinesDqlUpdater.append("SET gl_account_number="
								+ invoiceLineCol.getString("gl_account_number")
								+ " ");
						invoiceLinesDqlUpdater.append("SET gl_sales_tax_code="
								+ invoiceLineCol.getString("gl_sales_tax_code")
								+ " ");
						invoiceLinesDqlUpdater.append("SET internal_order="
								+ invoiceLineCol.getString("internal_order")
								+ " ");
						invoiceLinesDqlUpdater.append("SET material="
								+ invoiceLineCol.getString("material") + " ");
						invoiceLinesDqlUpdater
								.append("SET partner_profit_center="
										+ invoiceLineCol
												.getString("partner_profit_center")
										+ " ");
						invoiceLinesDqlUpdater.append("SET plant="
								+ invoiceLineCol.getString("plant") + " ");
						invoiceLinesDqlUpdater.append("SET profit_center="
								+ invoiceLineCol.getString("profit_center")
								+ " ");
						invoiceLinesDqlUpdater.append("SET quantity="
								+ invoiceLineCol.getString("quantity") + " ");
						invoiceLinesDqlUpdater
								.append("SET sales_order="
										+ invoiceLineCol
												.getString("sales_order") + " ");
						invoiceLinesDqlUpdater.append("SET sales_tax_order="
								+ invoiceLineCol.getString("sales_tax_order")
								+ " ");
						invoiceLinesDqlUpdater.append("SET tax_gl_account="
								+ invoiceLineCol.getString("tax_gl_account")
								+ " ");
						invoiceLinesDqlUpdater.append("SET tax_item_number="
								+ invoiceLineCol.getString("tax_item_number")
								+ " ");
						invoiceLinesDqlUpdater.append("SET tax_rate="
								+ invoiceLineCol.getString("tax_rate") + " ");
						invoiceLinesDqlUpdater.append("SET vendor_item_number="
								+ invoiceLineCol
										.getString("vendor_item_number") + " ");
						invoiceLinesDqlUpdater.append("SET wbs="
								+ invoiceLineCol.getString("wbs") + " ");
						invoiceLinesDqlUpdater.append(" WHERE r_object_id = '"
								+ invoiceLineCol.getString("r_object_id")
								+ "' ");
						logger.debug("Updating invoice line: "
								+ invoiceLinesDqlUpdater.toString());
						DQLquery.setDQL(invoiceLinesDqlUpdater.toString());
						DQLquery.execute(dctmInstance.getSession(),
								IDfQuery.DF_EXEC_QUERY);
						updatingLine = true;
					}
				}
				if (updatingLine == true)
					updatingLine = false;
				else {
					// Bloc to create an invoice line if this one doesn't exist
					StringBuilder invoiceLinesDqlCreator = new StringBuilder(
							"CREATE invoice_line OBJECTS ");
					invoiceLinesDqlCreator.append("SET amount="
							+ invoiceLineCol.getString("amount") + " ");
					invoiceLinesDqlCreator
							.append("SET assignement_vendor_number="
									+ invoiceLineCol
											.getString("assignement_vendor_number")
									+ " ");
					invoiceLinesDqlCreator.append("SET base_line_date="
							+ invoiceLineCol.getString("base_line_date") + " ");
					invoiceLinesDqlCreator.append("SET cost_center="
							+ invoiceLineCol.getString("cost_center") + " ");
					invoiceLinesDqlCreator.append("SET gl_item_text="
							+ invoiceLineCol.getString("gl_item_text") + " ");
					invoiceLinesDqlCreator.append("SET gl_account="
							+ invoiceLineCol.getString("gl_account") + " ");
					invoiceLinesDqlCreator.append("SET gl_account_number="
							+ invoiceLineCol.getString("gl_account_number")
							+ " ");
					invoiceLinesDqlCreator.append("SET gl_sales_tax_code="
							+ invoiceLineCol.getString("gl_sales_tax_code")
							+ " ");
					invoiceLinesDqlCreator.append("SET internal_order="
							+ invoiceLineCol.getString("internal_order") + " ");
					invoiceLinesDqlCreator.append("SET material="
							+ invoiceLineCol.getString("material") + " ");
					invoiceLinesDqlCreator.append("SET partner_profit_center="
							+ invoiceLineCol.getString("partner_profit_center")
							+ " ");
					invoiceLinesDqlCreator.append("SET plant="
							+ invoiceLineCol.getString("plant") + " ");
					invoiceLinesDqlCreator.append("SET profit_center="
							+ invoiceLineCol.getString("profit_center") + " ");
					invoiceLinesDqlCreator.append("SET quantity="
							+ invoiceLineCol.getString("quantity") + " ");
					invoiceLinesDqlCreator.append("SET sales_order="
							+ invoiceLineCol.getString("sales_order") + " ");
					invoiceLinesDqlCreator
							.append("SET sales_tax_order="
									+ invoiceLineCol
											.getString("sales_tax_order") + " ");
					invoiceLinesDqlCreator.append("SET tax_gl_account="
							+ invoiceLineCol.getString("tax_gl_account") + " ");
					invoiceLinesDqlCreator
							.append("SET tax_item_number="
									+ invoiceLineCol
											.getString("tax_item_number") + " ");
					invoiceLinesDqlCreator.append("SET tax_rate="
							+ invoiceLineCol.getString("tax_rate") + " ");
					invoiceLinesDqlCreator.append("SET vendor_item_number="
							+ invoiceLineCol.getString("vendor_item_number")
							+ " ");
					invoiceLinesDqlCreator.append("SET wbs="
							+ invoiceLineCol.getString("wbs") + " ");
					invoiceLinesDqlCreator.append(" WHERE r_object_id = '"
							+ invoiceLineCol.getString("r_object_id") + "' ");
					logger.debug("Creating invoice line: "
							+ invoiceLinesDqlCreator.toString());
					DQLquery.setDQL(invoiceLinesDqlCreator.toString());
					DQLquery.execute(dctmInstance.getSession(),
							IDfQuery.DF_EXEC_QUERY);
				}

			}
		}
		// Updating supplier detail
		// Getting supplier detail
		// check if the supplier detail exist then update else create the
		// supplier detail
		IDfQuery queryGetInvoiceSupplier = new DfQuery();
		queryGetInvoiceSupplier.setDQL(DQL_QUERY_RETRIEVE_INVOICE_SUPPLIER
				+ pInvoice.getrObjectId() + "'");
		IDfCollection invoiceSupplierDetail = queryGetInvoiceSupplier.execute(
				session, IDfQuery.DF_READ_QUERY);
		if (null != invoiceSupplierDetail) {
			while (invoiceSupplierDetail.next()) {
				StringBuilder SupplierDqlUpdater = new StringBuilder(
						"UPDATE supplier OBJECTS ");
				SupplierDqlUpdater.append("SET selected_iban="
						+ invoiceSupplierDetail.getString("selected_iban")
						+ " ");
				SupplierDqlUpdater
						.append("SET supplier_cpd="
								+ invoiceSupplierDetail
										.getString("supplier_cpd") + " ");
				SupplierDqlUpdater.append("SET supplier_industry="
						+ invoiceSupplierDetail.getString("supplier_industry")
						+ " ");
				SupplierDqlUpdater.append("SET supplier_invoice_address="
						+ invoiceSupplierDetail
								.getString("supplier_invoice_address") + " ");
				SupplierDqlUpdater.append("SET supplier_invoice_city="
						+ invoiceSupplierDetail
								.getString("supplier_invoice_city") + " ");
				SupplierDqlUpdater.append("SET supplier_invoice_country="
						+ invoiceSupplierDetail
								.getString("supplier_invoice_country") + " ");
				SupplierDqlUpdater.append("SET supplier_invoice_email="
						+ invoiceSupplierDetail
								.getString("supplier_invoice_email") + " ");
				SupplierDqlUpdater.append("SET supplier_invoice_post_code="
						+ invoiceSupplierDetail
								.getString("supplier_invoice_post_code") + " ");
				SupplierDqlUpdater.append("SET supplier_name="
						+ invoiceSupplierDetail.getString("supplier_name")
						+ " ");
				SupplierDqlUpdater.append("SET supplier_tax_number="
						+ invoiceSupplierDetail
								.getString("supplier_tax_number") + " ");
				SupplierDqlUpdater.append("SET supplier_vat_number="
						+ invoiceSupplierDetail
								.getString("supplier_vat_number") + " ");
				SupplierDqlUpdater
						.append(" WHERE r_object_id = '"
								+ invoiceSupplierDetail
										.getString("r_object_id") + "' ");
				logger.debug("Updating SupplierDqlUpdater: "
						+ SupplierDqlUpdater.toString());
				DQLquery.setDQL(SupplierDqlUpdater.toString());
				DQLquery.execute(dctmInstance.getSession(),
						IDfQuery.DF_EXEC_QUERY);
			}
		}
		// else{
		// //TODO
		// //Create invoice supplier
		// StringBuilder supplierDqlCreator = new
		// StringBuilder("CREATE supplier OBJECTS ");
		// supplierDqlCreator.append("SET selected_iban=" +
		// invoiceSupplierDetail.getString("selected_iban") + " ");
		// supplierDqlCreator.append("SET supplier_cpd=" +
		// invoiceSupplierDetail.getString("supplier_cpd") + " ");
		// supplierDqlCreator.append("SET supplier_industry=" +
		// invoiceSupplierDetail.getString("supplier_industry") + " ");
		// supplierDqlCreator.append("SET supplier_invoice_address=" +
		// invoiceSupplierDetail.getString("supplier_invoice_address") + " ");
		// supplierDqlCreator.append("SET supplier_invoice_city=" +
		// invoiceSupplierDetail.getString("supplier_invoice_city") + " ");
		// supplierDqlCreator.append("SET supplier_invoice_country=" +
		// invoiceSupplierDetail.getString("supplier_invoice_country") + " ");
		// supplierDqlCreator.append("SET supplier_invoice_email=" +
		// invoiceSupplierDetail.getString("supplier_invoice_email") + " ");
		// supplierDqlCreator.append("SET supplier_invoice_post_code=" +
		// invoiceSupplierDetail.getString("supplier_invoice_post_code") + " ");
		// supplierDqlCreator.append("SET supplier_name=" +
		// invoiceSupplierDetail.getString("supplier_name") + " ");
		// supplierDqlCreator.append("SET supplier_tax_number=" +
		// invoiceSupplierDetail.getString("supplier_tax_number") + " ");
		// supplierDqlCreator.append("SET supplier_vat_number=" +
		// invoiceSupplierDetail.getString("supplier_vat_number") + " ");
		// supplierDqlCreator.append(" WHERE r_object_id = '" +
		// invoiceSupplierDetail.getString("r_object_id") + "' ");
		// logger.debug("Updating SupplierDqlUpdater: " +
		// supplierDqlCreator.toString());
		// DQLquery.setDQL(supplierDqlCreator.toString());
		// DQLquery.execute(dctmInstance.getSession(), IDfQuery.DF_EXEC_QUERY);
		// }

		// Updating Purchase Order Detail
		IDfQuery queryGetInvoicePO = new DfQuery();
		queryGetInvoicePO.setDQL(DQL_QUERY_RETRIEVE_INVOICE_PO
				+ pInvoice.getrObjectId() + "'");
		IDfCollection invoicePODetail = queryGetInvoicePO.execute(session,
				IDfQuery.DF_READ_QUERY);
		if (null != invoicePODetail) {
			while (invoicePODetail.next()) {
				StringBuilder poDetailDqlUpdater = new StringBuilder(
						"UPDATE supplier OBJECTS ");
				poDetailDqlUpdater.append("SET document_type="
						+ invoicePODetail.getString("document_type") + " ");
				poDetailDqlUpdater.append("SET po_number="
						+ invoicePODetail.getString("po_number") + " ");
				poDetailDqlUpdater
						.append("SET po_number_position="
								+ invoicePODetail
										.getString("po_number_position") + " ");
				poDetailDqlUpdater.append("SET vendor_name="
						+ invoicePODetail.getString("vendor_name") + " ");
				poDetailDqlUpdater.append("SET vendor_number="
						+ invoicePODetail.getString("vendor_number") + " ");
				poDetailDqlUpdater
						.append(" WHERE r_object_id = '"
								+ invoiceSupplierDetail
										.getString("r_object_id") + "' ");
				logger.debug("Updating PODetailDqlUpdater: "
						+ poDetailDqlUpdater.toString());
				DQLquery.setDQL(poDetailDqlUpdater.toString());
				DQLquery.execute(dctmInstance.getSession(),
						IDfQuery.DF_EXEC_QUERY);
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
								"UPDATE bank_detail OBJECTS ");
						invoiceBanksDqlUpdater.append("SET bank_name="
								+ invoiceBankDetail.getString("bank_name")
								+ " ");
						invoiceBanksDqlUpdater.append("SET supplier_iban="
								+ invoiceBankDetail.getString("supplier_iban")
								+ " ");
						invoiceBanksDqlUpdater.append("SET supplier_name="
								+ invoiceBankDetail.getString("supplier_name")
								+ " ");
						invoiceBanksDqlUpdater.append(" WHERE r_object_id = '"
								+ invoiceBankDetail.getString("r_object_id")
								+ "' ");
						logger.debug("Updating bank detail : "
								+ invoiceBanksDqlUpdater.toString());
						DQLquery.setDQL(invoiceBanksDqlUpdater.toString());
						DQLquery.execute(dctmInstance.getSession(),
								IDfQuery.DF_EXEC_QUERY);
						updatingBank = true;
					}
				}
				if (updatingBank == true)
					updatingBank = false;
				else {
					// Bloc to create an invoice line if this one doesn't exist
					StringBuilder invoiceBankDqlCreator = new StringBuilder(
							"CREATE bank_detail OBJECTS ");
					invoiceBankDqlCreator.append("SET bank_name="
							+ invoiceBankDetail.getString("bank_name") + " ");
					invoiceBankDqlCreator.append("SET supplier_iban="
							+ invoiceBankDetail.getString("supplier_iban")
							+ " ");
					invoiceBankDqlCreator.append("SET supplier_name="
							+ invoiceBankDetail.getString("supplier_name")
							+ " ");
					invoiceBankDqlCreator
							.append(" WHERE r_object_id = '"
									+ invoiceBankDetail
											.getString("r_object_id") + "' ");
					logger.debug("Creating Bank Detail: "
							+ invoiceBankDqlCreator.toString());
					DQLquery.setDQL(invoiceBankDqlCreator.toString());
					DQLquery.execute(dctmInstance.getSession(),
							IDfQuery.DF_EXEC_QUERY);
				}

			}
		}

		// Populate Approver list
		IDfQuery queryGetApprovers = new DfQuery();
		queryGetApprovers.setDQL(DQL_QUERY_RETRIEVE_APPROVER
				+ pInvoice.getrObjectId() + "'");
		IDfCollection invoiceApproverList = queryGetApprovers.execute(session,
				IDfQuery.DF_READ_QUERY);
		if (null != invoiceApproverList) {
			pInvoice.setApproverGroupList(new ArrayList<Message>());
			while (invoiceApproverList.next()) {
				Message message = new Message();
				message.setLogin(invoiceApproverList.getString("login"));
				message.setContentText(invoiceApproverList.getString("comment"));
				if (null != invoiceApproverList.getString("sequence"))
					message.setSequence(Integer.parseInt(invoiceApproverList
							.getString("sequence")));
				// TODO
				// verifier la bonne conversion de string vers date
				message.setDate(dateUtils.stringToDate(
						invoiceApproverList.getString("date"), "dd-mm-yyyy"));
				pInvoice.getApproverGroupList().add(message);
			}
		}

		// Populate Processor list
		IDfQuery queryGetProcessors = new DfQuery();
		queryGetProcessors.setDQL(DQL_QUERY_RETRIEVE_PROCESSOR
				+ pInvoice.getrObjectId() + "'");
		IDfCollection invoiceProcessorList = queryGetProcessors.execute(
				session, IDfQuery.DF_READ_QUERY);
		if (null != invoiceProcessorList) {
			pInvoice.setProcessorDecision(new ArrayList<Message>());
			while (invoiceProcessorList.next()) {
				Message message = new Message();
				message.setLogin(invoiceApproverList.getString("login"));
				message.setContentText(invoiceApproverList.getString("comment"));
				if (null != invoiceApproverList.getString("sequence"))
					message.setSequence(Integer.parseInt(invoiceApproverList
							.getString("sequence")));
				// TODO
				// verifier la bonne conversion de string vers date
				message.setDate(dateUtils.stringToDate(
						invoiceApproverList.getString("date"), "dd-mm-yyyy"));
				pInvoice.getProcessorDecision().add(message);
			}
		}
		return pInvoice;
	}

	public List<Invoice> getInvoicesByStatut(Integer status)
			throws DfIdentityException, DfAuthenticationException,
			DfPrincipalException, DfServiceException, DfException,
			JCoException, IOException {
		IDfQuery queryGetInvoiceByStatus = new DfQuery();
		IDfQuery DQLquery = new DfQuery();
		List<Invoice> listOfInvoices = new ArrayList<Invoice>();
		queryGetInvoiceByStatus.setDQL(DQL_QUERY_RETRIEVE_INVOICE_BY_STATUS
				+ status + "'");
		IDfCollection invoiceCol = queryGetInvoiceByStatus.execute(
				dctmInstance.getSession(), IDfQuery.DF_READ_QUERY);
		if (null != invoiceCol) {
			for (int i = 0; i < invoiceCol.getAttrCount(); i++) {
				Invoice invoiceInstance = new Invoice();
				invoiceInstance.setrObjectId(invoiceCol
						.getString("r_object_id"));
				listOfInvoices.add(read(invoiceInstance));
			}
		}
		logger.debug("Retrieving Invoices by Id: "
				+ queryGetInvoiceByStatus.toString());
		DQLquery.setDQL(queryGetInvoiceByStatus.toString());
		DQLquery.execute(dctmInstance.getSession(), IDfQuery.DF_EXEC_QUERY);
		return listOfInvoices;
	}

	public Invoice retrieveInvoice(Invoice pInvoice) throws Exception {
		return read(pInvoice);
	}
}
