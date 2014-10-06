package com.csc.vim.framework.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "threshold")
@XmlAccessorType(XmlAccessType.FIELD)
public class Threshold {

	private String ThresholdKey;
	private String ThresholdDesc;
	private String ThresholdAmount;
	private String ThresholdUnit;
	
	
	public String getThresholdKey() {
		return ThresholdKey;
	}

	@XmlElement
	public void setThresholdKey(String thresholdKey) {
		ThresholdKey = thresholdKey;
	}


	public String getThresholdDesc() {
		return ThresholdDesc;
	}

	@XmlElement
	public void setThresholdDesc(String thresholdDesc) {
		ThresholdDesc = thresholdDesc;
	}


	public String getThresholdAmount() {
		return ThresholdAmount;
	}

	@XmlElement
	public void setThresholdAmount(String thresholdAmount) {
		ThresholdAmount = thresholdAmount;
	}


	public String getThresholdUnit() {
		return ThresholdUnit;
	}

	@XmlElement
	public void setThresholdUnit(String thresholdUnit) {
		ThresholdUnit = thresholdUnit;
	}


	public Threshold(String thresholdKey, String thresholdDesc,
			String thresholdAmount, String thresholdUnit) {
		super();
		ThresholdKey = thresholdKey;
		ThresholdDesc = thresholdDesc;
		ThresholdAmount = thresholdAmount;
		ThresholdUnit = thresholdUnit;
	}


	public Threshold(){
		
	}
	
}
