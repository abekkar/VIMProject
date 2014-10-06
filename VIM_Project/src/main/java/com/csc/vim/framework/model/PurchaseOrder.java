package com.csc.vim.framework.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "purchaseOrder")
@XmlAccessorType(XmlAccessType.FIELD)
public class PurchaseOrder {

	private String rObjectId;
	private String supplierNumber;
	private String poDocumentType;
	private String supplierName;
	private String poNumber;
	private String poNumberPosition;
	
	public String getrObjectId() {
		return rObjectId;
	}
	
	public void setrObjectId(String rObjectId) {
		this.rObjectId = rObjectId;
	}
	public String getPoNumber() {
		return poNumber;
	}
	
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	
	public String getPoNumberPosition() {
		return poNumberPosition;
	}
	
	public void setPoNumberPosition(String poNumberPosition) {
		this.poNumberPosition = poNumberPosition;
	}
	
	public String getSupplierNumber() {
		return supplierNumber;
	}
	
	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}
	public String getPoDocumentType() {
		return poDocumentType;
	}
	
	public void setPoDocumentType(String poDocumentType) {
		this.poDocumentType = poDocumentType;
	}
	public String getSupplierName() {
		return supplierName;
	}
	
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public PurchaseOrder(String rObjectId, String supplierNumber,
			String poDocumentType, String supplierName, String poNumber,
			String poNumberPosition) {
		super();
		this.rObjectId = rObjectId;
		this.supplierNumber = supplierNumber;
		this.poDocumentType = poDocumentType;
		this.supplierName = supplierName;
		this.poNumber = poNumber;
		this.poNumberPosition = poNumberPosition;
	}

	public PurchaseOrder(){
		
	}
	
}
