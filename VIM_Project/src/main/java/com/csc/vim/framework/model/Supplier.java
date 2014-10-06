package com.csc.vim.framework.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "supplier")
@XmlAccessorType(XmlAccessType.FIELD)
public class Supplier {

	private String rObjectId;
	private String supplierIndustry;
	private String supplierSelectedIban;
	private String supplierInvoiceAddress;
	private String supplierInvoicePostCode;
	private String supplierInvoiceCity;
	private String supplierInvoiceCountry;
	private String supplierInvoiceEmail;
	private boolean supplierCPD;
	private String supplierTaxNumber;
	private String supplierName;
	private String supplierVatNumber;
	
	public String getrObjectId() {
		return rObjectId;
	}
	public void setrObjectId(String rObjectId) {
		this.rObjectId = rObjectId;
	}
	public String getSupplierIndustry() {
		return supplierIndustry;
	}
	public void setSupplierIndustry(String supplierIndustry) {
		this.supplierIndustry = supplierIndustry;
	}
	public String getSupplierInvoiceAddress() {
		return supplierInvoiceAddress;
	}
	public void setSupplierInvoiceAddress(String supplierInvoiceAddress) {
		this.supplierInvoiceAddress = supplierInvoiceAddress;
	}
	public String getSupplierInvoicePostCode() {
		return supplierInvoicePostCode;
	}
	public void setSupplierInvoicePostCode(String supplierInvoicePostCode) {
		this.supplierInvoicePostCode = supplierInvoicePostCode;
	}
	public String getSupplierInvoiceCity() {
		return supplierInvoiceCity;
	}
	public void setSupplierInvoiceCity(String supplierInvoiceCity) {
		this.supplierInvoiceCity = supplierInvoiceCity;
	}
	public String getSupplierInvoiceCountry() {
		return supplierInvoiceCountry;
	}
	public void setSupplierInvoiceCountry(String supplierInvoiceCountry) {
		this.supplierInvoiceCountry = supplierInvoiceCountry;
	}
	public String getSupplierInvoiceEmail() {
		return supplierInvoiceEmail;
	}
	public void setSupplierInvoiceEmail(String supplierInvoiceEmail) {
		this.supplierInvoiceEmail = supplierInvoiceEmail;
	}
	public String getSupplierTaxNumber() {
		return supplierTaxNumber;
	}
	public void setSupplierTaxNumber(String supplierTaxNumber) {
		this.supplierTaxNumber = supplierTaxNumber;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierVatNumber() {
		return supplierVatNumber;
	}
	public void setSupplierVatNumber(String supplierVatNumber) {
		this.supplierVatNumber = supplierVatNumber;
	}
	public String getSupplierSelectedIban() {
		return supplierSelectedIban;
	}
	public void setSupplierSelectedIban(String supplierSelectedIban) {
		this.supplierSelectedIban = supplierSelectedIban;
	}
	public boolean isSupplierCPD() {
		return supplierCPD;
	}
	public void setSupplierCPD(boolean supplierCPD) {
		this.supplierCPD = supplierCPD;
	}
	public Supplier(String rObjectId, String supplierIndustry,
			String supplierSelectedIban, String supplierInvoiceAddress,
			String supplierInvoicePostCode, String supplierInvoiceCity,
			String supplierInvoiceCountry, String supplierInvoiceEmail,
			boolean supplierCPD, String supplierTaxNumber, String supplierName,
			String supplierVatNumber) {
		super();
		this.rObjectId = rObjectId;
		this.supplierIndustry = supplierIndustry;
		this.supplierSelectedIban = supplierSelectedIban;
		this.supplierInvoiceAddress = supplierInvoiceAddress;
		this.supplierInvoicePostCode = supplierInvoicePostCode;
		this.supplierInvoiceCity = supplierInvoiceCity;
		this.supplierInvoiceCountry = supplierInvoiceCountry;
		this.supplierInvoiceEmail = supplierInvoiceEmail;
		this.supplierCPD = supplierCPD;
		this.supplierTaxNumber = supplierTaxNumber;
		this.supplierName = supplierName;
		this.supplierVatNumber = supplierVatNumber;
	}
	
	public Supplier(){
		
	}
	

}
