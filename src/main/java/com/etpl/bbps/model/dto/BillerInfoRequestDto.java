package com.etpl.bbps.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "billerInfoRequest")
public class BillerInfoRequestDto {
    private String billerId;

    @XmlElement
    public String getBillerId() {
        return billerId;
    }

    public void setBillerId(String billerId) {
        this.billerId = billerId;
    }
}
