package com.csc.vim.framework.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
//@Configuration
//@PropertySource("classpath:parameters.properties")
public class Parameters {
	
	@Value("${dfc.globalregistry.password}")
	public String dctmPassword;
	@Value("${dfc.globalregistry.repository}")
	public String dctmRepository;
	@Value("${dfc.globalregistry.username}")
	public String dctmUsername;
	@Value("${dfc.docbroker.host[0]}")
	public String dctmHost;
	@Value("${dfc.docbroker.port[0]}")
	public String dctmPort;
	
	@Value("${dctm.invoice.UCT}")
	public String INV_DCTM_UCT;
	
	@Value("${dctm.invoice.LCT}")
	public String INV_DCTM_LCT;
	
	@Value("${dctm.invoice.status.zero}")
	public String INV_DCTM_STATUS_ZERO;
	
	@Value("${dctm.invoice.status.one}")
	public String INV_DCTM_STATUS_ONE;
	
	@Value("${dctm.invoice.status.two}")
	public String INV_DCTM_STATUS_TWO;
	
	@Value("${dctm.invoice.status.three}")
	public String INV_DCTM_STATUS_THREE;
	
	@Value("${dctm.invoice.status.four}")
	public String INV_DCTM_STATUS_FOUR;
	
	@Value("${dctm.invoice.status.five}")
	public String INV_DCTM_STATUS_FIVE;
	
	@Value("${dctm.invoice.status.six}")
	public String INV_DCTM_STATUS_SIX;
	
	@Value("${dctm.invoice.status.seven}")
	public String INV_DCTM_STATUS_SEVEN;
	
	@Value("${dctm.invoice.status.eight}")
	public String INV_DCTM_STATUS_EIGHT;
	
	@Value("${dctm.invoice.status.nine}")
	public String INV_DCTM_STATUS_NINE;

	
	@Value("${sap.invoice.fi.busact}")
	public String busAct;
	
	@Value("${dctm_repositoryName}")
	public String dctmRepositoryName;
	
	@Value("${sap.invoice.mm.DOC_TYPE_DP}")
	public String downPayment;

	@Value("${sap.invoice.py.mode}")
	public String paymentMode;
	
	@Value("${sap.invoice.py.condition}")
	public String paymentCondition;
	
	@Value("${sap.destination.datafile}")
	public String sapDataFile;
	
	@Value("${sap.invoice.mm.DOC_TYPE}")
	public String MM_DOC_TYPE;
	
	@Value("${sap.invoice.ind.Invoice_IND}")
	public String Invoice_IND;
	
	@Value("${val.sap.JCO_ASHOST}")
	public String val_sap_jco_ahost;

	@Value("${val.sap.JCO_SYSNR}")
	public String val_sap_jco_sysnr;

	@Value("${val.sap.JCO_CLIENT}")
	public String val_sap_jco_client;

	@Value("${val.sap.JCO_USER}")
	public String val_sap_jco_user;

	@Value("${val.sap.JCO_PASSWD}")
	public String val_sap_jco_passwd;

	@Value("${val.sap.JCO_LANG}")
	public String val_sap_jco_lang;

	@Value("${val.sap.invoice.AR.ARCHIV_ID}")
	public String ARCHIV_ID;

	@Value("${sap.invoice.AR.AR_OBJECT}")
	public String AR_OBJECT;

	@Value("${sap.invoice.AR.SAP_OBJECT}")
	public String SAP_OBJECT;

	public String getMM_DOC_TYPE() {
		return MM_DOC_TYPE;
	}

	public void setMM_DOC_TYPE(String mM_DOC_TYPE) {
		MM_DOC_TYPE = mM_DOC_TYPE;
	}

	public String getInvoice_IND() {
		return Invoice_IND;
	}

	public void setInvoice_IND(String invoice_IND) {
		Invoice_IND = invoice_IND;
	}

	public String getVal_sap_jco_ahost() {
		return val_sap_jco_ahost;
	}

	public void setVal_sap_jco_ahost(String val_sap_jco_ahost) {
		this.val_sap_jco_ahost = val_sap_jco_ahost;
	}

	public String getVal_sap_jco_sysnr() {
		return val_sap_jco_sysnr;
	}

	public void setVal_sap_jco_sysnr(String val_sap_jco_sysnr) {
		this.val_sap_jco_sysnr = val_sap_jco_sysnr;
	}

	public String getVal_sap_jco_client() {
		return val_sap_jco_client;
	}

	public void setVal_sap_jco_client(String val_sap_jco_client) {
		this.val_sap_jco_client = val_sap_jco_client;
	}

	public String getVal_sap_jco_user() {
		return val_sap_jco_user;
	}

	public void setVal_sap_jco_user(String val_sap_jco_user) {
		this.val_sap_jco_user = val_sap_jco_user;
	}

	public String getVal_sap_jco_passwd() {
		return val_sap_jco_passwd;
	}

	public void setVal_sap_jco_passwd(String val_sap_jco_passwd) {
		this.val_sap_jco_passwd = val_sap_jco_passwd;
	}

	public String getVal_sap_jco_lang() {
		return val_sap_jco_lang;
	}

	public void setVal_sap_jco_lang(String val_sap_jco_lang) {
		this.val_sap_jco_lang = val_sap_jco_lang;
	}

	public String getARCHIV_ID() {
		return ARCHIV_ID;
	}

	public void setARCHIV_ID(String aRCHIV_ID) {
		ARCHIV_ID = aRCHIV_ID;
	}

	public String getAR_OBJECT() {
		return AR_OBJECT;
	}

	public void setAR_OBJECT(String aR_OBJECT) {
		AR_OBJECT = aR_OBJECT;
	}

	public String getSAP_OBJECT() {
		return SAP_OBJECT;
	}

	public void setSAP_OBJECT(String sAP_OBJECT) {
		SAP_OBJECT = sAP_OBJECT;
	}

	public String getSapDataFile() {
		return sapDataFile;
	}

	public void setSapDataFile(String sapDataFile) {
		this.sapDataFile = sapDataFile;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentCondition() {
		return paymentCondition;
	}

	public void setPaymentCondition(String paymentCondition) {
		this.paymentCondition = paymentCondition;
	}

	public String getBusAct() {
		return busAct;
	}

	public void setBusAct(String busAct) {
		this.busAct = busAct;
	}

	public String getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(String downPayment) {
		this.downPayment = downPayment;
	}

	public String getDctmRepositoryName() {
		return dctmRepositoryName;
	}

	public void setDctmRepositoryName(String dctmRepositoryName) {
		this.dctmRepositoryName = dctmRepositoryName;
	}

	public String getINV_DCTM_STATUS_ZERO() {
		return INV_DCTM_STATUS_ZERO;
	}

	public void setINV_DCTM_STATUS_ZERO(String iNV_DCTM_STATUS_ZERO) {
		INV_DCTM_STATUS_ZERO = iNV_DCTM_STATUS_ZERO;
	}

	public String getINV_DCTM_STATUS_ONE() {
		return INV_DCTM_STATUS_ONE;
	}

	public void setINV_DCTM_STATUS_ONE(String iNV_DCTM_STATUS_ONE) {
		INV_DCTM_STATUS_ONE = iNV_DCTM_STATUS_ONE;
	}

	public String getINV_DCTM_STATUS_TWO() {
		return INV_DCTM_STATUS_TWO;
	}

	public void setINV_DCTM_STATUS_TWO(String iNV_DCTM_STATUS_TWO) {
		INV_DCTM_STATUS_TWO = iNV_DCTM_STATUS_TWO;
	}

	public String getINV_DCTM_STATUS_THREE() {
		return INV_DCTM_STATUS_THREE;
	}

	public void setINV_DCTM_STATUS_THREE(String iNV_DCTM_STATUS_THREE) {
		INV_DCTM_STATUS_THREE = iNV_DCTM_STATUS_THREE;
	}

	public String getINV_DCTM_STATUS_FOUR() {
		return INV_DCTM_STATUS_FOUR;
	}

	public void setINV_DCTM_STATUS_FOUR(String iNV_DCTM_STATUS_FOUR) {
		INV_DCTM_STATUS_FOUR = iNV_DCTM_STATUS_FOUR;
	}

	public String getINV_DCTM_STATUS_FIVE() {
		return INV_DCTM_STATUS_FIVE;
	}

	public void setINV_DCTM_STATUS_FIVE(String iNV_DCTM_STATUS_FIVE) {
		INV_DCTM_STATUS_FIVE = iNV_DCTM_STATUS_FIVE;
	}

	public String getINV_DCTM_STATUS_SIX() {
		return INV_DCTM_STATUS_SIX;
	}

	public void setINV_DCTM_STATUS_SIX(String iNV_DCTM_STATUS_SIX) {
		INV_DCTM_STATUS_SIX = iNV_DCTM_STATUS_SIX;
	}

	public String getINV_DCTM_STATUS_SEVEN() {
		return INV_DCTM_STATUS_SEVEN;
	}

	public void setINV_DCTM_STATUS_SEVEN(String iNV_DCTM_STATUS_SEVEN) {
		INV_DCTM_STATUS_SEVEN = iNV_DCTM_STATUS_SEVEN;
	}

	public String getINV_DCTM_STATUS_EIGHT() {
		return INV_DCTM_STATUS_EIGHT;
	}

	public void setINV_DCTM_STATUS_EIGHT(String iNV_DCTM_STATUS_EIGHT) {
		INV_DCTM_STATUS_EIGHT = iNV_DCTM_STATUS_EIGHT;
	}

	public String getINV_DCTM_STATUS_NINE() {
		return INV_DCTM_STATUS_NINE;
	}

	public void setINV_DCTM_STATUS_NINE(String iNV_DCTM_STATUS_NINE) {
		INV_DCTM_STATUS_NINE = iNV_DCTM_STATUS_NINE;
	}

	public String getINV_DCTM_UCT() {
		return INV_DCTM_UCT;
	}

	public void setINV_DCTM_UCT(String iNV_DCTM_UCT) {
		INV_DCTM_UCT = iNV_DCTM_UCT;
	}

	public String getINV_DCTM_LCT() {
		return INV_DCTM_LCT;
	}

	public void setINV_DCTM_LCT(String iNV_DCTM_LCT) {
		INV_DCTM_LCT = iNV_DCTM_LCT;
	}

	public String getDctmPassword() {
		return dctmPassword;
	}

	public void setDctmPassword(String dctmPassword) {
		this.dctmPassword = dctmPassword;
	}

	public String getDctmRepository() {
		return dctmRepository;
	}

	public void setDctmRepository(String dctmRepository) {
		this.dctmRepository = dctmRepository;
	}

	public String getDctmUsername() {
		return dctmUsername;
	}

	public void setDctmUsername(String dctmUsername) {
		this.dctmUsername = dctmUsername;
	}

	public String getDctmHost() {
		return dctmHost;
	}

	public void setDctmHost(String dctmHost) {
		this.dctmHost = dctmHost;
	}

	public String getDctmPort() {
		return dctmPort;
	}

	public void setDctmPort(String dctmPort) {
		this.dctmPort = dctmPort;
	}
	
	
}
