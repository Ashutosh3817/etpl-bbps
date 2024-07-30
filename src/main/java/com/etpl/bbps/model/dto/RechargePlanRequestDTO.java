package com.etpl.bbps.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="rechargePlanRequest")
public class RechargePlanRequestDTO {
	private String billerId;
	private String circle;
	
	@XmlElement(name="billerId")
	public String getBillerId() {
		return billerId;
	}
	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}
	@XmlElement(name="circle")
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	
	
}
