package com.etpl.bbps.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="BillAvenueBillerPaymentChannel")
@Table(name = "TL_biller_Payment_Channel")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillAvenueBillerPaymentChannel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "biller_id")
    private BillAvenueBillerInfo billerInfo;

    @Column(name = "payment_channel_name")
    @XmlElement(name = "paymentChannelName")
    private String paymentChannelName;

    @Column(name = "min_amount")
    @XmlElement(name = "minAmount")
    private Integer minAmount;

    @Column(name = "max_amount")
    @XmlElement(name = "maxAmount")
    private Integer maxAmount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPaymentChannelName() {
		return paymentChannelName;
	}

	public void setPaymentChannelName(String paymentChannelName) {
		this.paymentChannelName = paymentChannelName;
	}

	public Integer getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(Integer minAmount) {
		this.minAmount = minAmount;
	}

	public Integer getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Integer maxAmount) {
		this.maxAmount = maxAmount;
	}
    
    
	

}