package com.etpl.bbps.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "billerInfoRequest")
public class BillerInfoRequestDto {

    private List<String> billerIds;

    @XmlElement(name = "billerId")
    public List<String> getBillerIds() {
        return billerIds;
    }

    public void setBillerIds(List<String> billerIds) {
        this.billerIds = billerIds;
    }
}
