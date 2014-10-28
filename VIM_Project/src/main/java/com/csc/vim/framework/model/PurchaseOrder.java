package com.csc.vim.framework.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.csc.vim.framework.common.model.DematObject;

@XmlRootElement(name = "purchaseOrder")
@XmlAccessorType(XmlAccessType.FIELD)
public class PurchaseOrder extends DematObject {

	// Business Field
	private String supplierNumber;
	private String poDocumentType;
	private String supplierName;
	private Date poDate;
	private String poNumber;
	private String poNumberPosition;
	
	
	private String sapId;				// Not Used for now ....

	public String getSapId() {
		return sapId;
	}

	public void setSapId(String sapId) {
		this.sapId = sapId;
	}

	public Date getPoDate() {
		return poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
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

	public PurchaseOrder(String rObjectId, 
			String supplierNumber,
			String poDocumentType, 
			String supplierName, 
			Date poDate,
			String poNumber,
			String poNumberPosition) {
		this.rObjectId = rObjectId;
		this.supplierNumber = supplierNumber;
		this.poDocumentType = poDocumentType;
		this.supplierName = supplierName;
		this.poDate = poDate;
		this.poNumber = poNumber;
		this.poNumberPosition = poNumberPosition;
	}

	public PurchaseOrder(){
		
	}
	
}
