package com.csc.vim.framework.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "invoiceLine")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceLine {

	private String rObjectId;
	private String glAccount;
	private String glItemText;
	private String glAccountNumber;
	private String glTaxAccount;
	private String vendorItemNumber;
	private int TaxItemNumber;
	private int taxRate;
	private Date baseLineDate;
	private String costCenter;
	private String profitCenter;
	private String partnerProfitCenter;
	private String internalOrder;
	private String material;
	private String plantNumber;
	private int quantity;
	private String salesOrder;
	private String wbs;
	private String vendorAssignmentNumber;
	private String salestaxCode;
	public String getrObjectId() {
		return rObjectId;
	}
	@XmlElement
	public void setrObjectId(String rObjectId) {
		this.rObjectId = rObjectId;
	}
	public String getGlAccount() {
		return glAccount;
	}
	@XmlElement
	public void setGlAccount(String glAccount) {
		this.glAccount = glAccount;
	}
	public String getGlItemText() {
		return glItemText;
	}
	@XmlElement
	public void setGlItemText(String glItemText) {
		this.glItemText = glItemText;
	}
	public String getGlAccountNumber() {
		return glAccountNumber;
	}
	@XmlElement
	public void setGlAccountNumber(String glAccountNumber) {
		this.glAccountNumber = glAccountNumber;
	}
	public String getGlTaxAccount() {
		return glTaxAccount;
	}
	@XmlElement
	public void setGlTaxAccount(String glTaxAccount) {
		this.glTaxAccount = glTaxAccount;
	}
	public String getVendorItemNumber() {
		return vendorItemNumber;
	}
	@XmlElement
	public void setVendorItemNumber(String vendorItemNumber) {
		this.vendorItemNumber = vendorItemNumber;
	}
	public int getTaxItemNumber() {
		return TaxItemNumber;
	}
	@XmlElement
	public void setTaxItemNumber(int taxItemNumber) {
		TaxItemNumber = taxItemNumber;
	}
	public int getTaxRate() {
		return taxRate;
	}
	@XmlElement
	public void setTaxRate(int taxRate) {
		this.taxRate = taxRate;
	}
	public Date getBaseLineDate() {
		return baseLineDate;
	}
	@XmlElement
	public void setBaseLineDate(Date baseLineDate) {
		this.baseLineDate = baseLineDate;
	}
	public String getCostCenter() {
		return costCenter;
	}
	@XmlElement
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getProfitCenter() {
		return profitCenter;
	}
	@XmlElement
	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
	}
	public String getPartnerProfitCenter() {
		return partnerProfitCenter;
	}
	@XmlElement
	public void setPartnerProfitCenter(String partnerProfitCenter) {
		this.partnerProfitCenter = partnerProfitCenter;
	}
	public String getInternalOrder() {
		return internalOrder;
	}
	@XmlElement
	public void setInternalOrder(String internalOrder) {
		this.internalOrder = internalOrder;
	}
	public String getMaterial() {
		return material;
	}
	@XmlElement
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getPlantNumber() {
		return plantNumber;
	}
	@XmlElement
	public void setPlantNumber(String plantNumber) {
		this.plantNumber = plantNumber;
	}
	public int getQuantity() {
		return quantity;
	}
	@XmlElement
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getSalesOrder() {
		return salesOrder;
	}
	@XmlElement
	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}
	public String getWbs() {
		return wbs;
	}
	@XmlElement
	public void setWbs(String wbs) {
		this.wbs = wbs;
	}
	public String getVendorAssignmentNumber() {
		return vendorAssignmentNumber;
	}
	@XmlElement
	public void setVendorAssignmentNumber(String vendorAssignmentNumber) {
		this.vendorAssignmentNumber = vendorAssignmentNumber;
	}
	public String getSalestaxCode() {
		return salestaxCode;
	}
	@XmlElement
	public void setSalestaxCode(String salestaxCode) {
		this.salestaxCode = salestaxCode;
	}
	public InvoiceLine(String rObjectId, String glAccount, String glItemText,
			String glAccountNumber, String glTaxAccount,
			String vendorItemNumber, int taxItemNumber, int taxRate,
			Date baseLineDate, String costCenter, String profitCenter,
			String partnerProfitCenter, String internalOrder, String material,
			String plantNumber, int quantity, String salesOrder, String wbs,
			String vendorAssignmentNumber, String salestaxCode) {
		super();
		this.rObjectId = rObjectId;
		this.glAccount = glAccount;
		this.glItemText = glItemText;
		this.glAccountNumber = glAccountNumber;
		this.glTaxAccount = glTaxAccount;
		this.vendorItemNumber = vendorItemNumber;
		TaxItemNumber = taxItemNumber;
		this.taxRate = taxRate;
		this.baseLineDate = baseLineDate;
		this.costCenter = costCenter;
		this.profitCenter = profitCenter;
		this.partnerProfitCenter = partnerProfitCenter;
		this.internalOrder = internalOrder;
		this.material = material;
		this.plantNumber = plantNumber;
		this.quantity = quantity;
		this.salesOrder = salesOrder;
		this.wbs = wbs;
		this.vendorAssignmentNumber = vendorAssignmentNumber;
		this.salestaxCode = salestaxCode;
	}
	public InvoiceLine() {

	}
		
}
