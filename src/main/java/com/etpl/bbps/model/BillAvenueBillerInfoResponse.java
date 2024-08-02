package com.etpl.bbps.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "billerInfoResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillAvenueBillerInfoResponse {

    @XmlElement(name = "responseCode")
    private String responseCode;

    @XmlElement(name = "biller")
    private List<BillAvenueBillerInfo> billersInfo;

    // Getters and Setters
    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<BillAvenueBillerInfo> getBillersInfo() {
        return billersInfo;
    }

    public void setBillersInfo(List<BillAvenueBillerInfo> billersInfo) {
        this.billersInfo = billersInfo;
    }
}