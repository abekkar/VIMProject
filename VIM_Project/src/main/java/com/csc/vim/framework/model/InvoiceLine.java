package com.csc.vim.framework.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
	
	public void setrObjectId(String rObjectId) {
		this.rObjectId = rObjectId;
	}
	public String getGlAccount() {
		return glAccount;
	}
	
	public void setGlAccount(String glAccount) {
		this.glAccount = glAccount;
	}
	public String getGlItemText() {
		return glItemText;
	}
	
	public void setGlItemText(String glItemText) {
		this.glItemText = glItemText;
	}
	public String getGlAccountNumber() {
		return glAccountNumber;
	}
	
	public void setGlAccountNumber(String glAccountNumber) {
		this.glAccountNumber = glAccountNumber;
	}
	public String getGlTaxAccount() {
		return glTaxAccount;
	}
	
	public void setGlTaxAccount(String glTaxAccount) {
		this.glTaxAccount = glTaxAccount;
	}
	public String getVendorItemNumber() {
		return vendorItemNumber;
	}
	
	public void setVendorItemNumber(String vendorItemNumber) {
		this.vendorItemNumber = vendorItemNumber;
	}
	public int getTaxItemNumber() {
		return TaxItemNumber;
	}
	
	public void setTaxItemNumber(int taxItemNumber) {
		TaxItemNumber = taxItemNumber;
	}
	public int getTaxRate() {
		return taxRate;
	}
	
	public void setTaxRate(int taxRate) {
		this.taxRate = taxRate;
	}
	public Date getBaseLineDate() {
		return baseLineDate;
	}
	
	public void setBaseLineDate(Date baseLineDate) {
		this.baseLineDate = baseLineDate;
	}
	public String getCostCenter() {
		return costCenter;
	}
	
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getProfitCenter() {
		return profitCenter;
	}
	
	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
	}
	public String getPartnerProfitCenter() {
		return partnerProfitCenter;
	}
	
	public void setPartnerProfitCenter(String partnerProfitCenter) {
		this.partnerProfitCenter = partnerProfitCenter;
	}
	public String getInternalOrder() {
		return internalOrder;
	}
	
	public void setInternalOrder(String internalOrder) {
		this.internalOrder = internalOrder;
	}
	public String getMaterial() {
		return material;
	}
	
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getPlantNumber() {
		return plantNumber;
	}
	
	public void setPlantNumber(String plantNumber) {
		this.plantNumber = plantNumber;
	}
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getSalesOrder() {
		return salesOrder;
	}
	
	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}
	public String getWbs() {
		return wbs;
	}
	
	public void setWbs(String wbs) {
		this.wbs = wbs;
	}
	public String getVendorAssignmentNumber() {
		return vendorAssignmentNumber;
	}
	
	public void setVendorAssignmentNumber(String vendorAssignmentNumber) {
		this.vendorAssignmentNumber = vendorAssignmentNumber;
	}
	public String getSalestaxCode() {
		return salestaxCode;
	}
	
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
