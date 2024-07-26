package com.etpl.bbps.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "PaymentChannels")
@Table(name = "tl_payment_channels")
public class PaymentChannels {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "PC_ID_FK")
	  private Long bId;
	
	@Column(name = "PAYMENT_CHANNEL")
	private String payment_channel;
	
	@Column(name = "MIN_LIMIT")
	private String min_limit;
	
	@Column(name = "MAX_LIMIT")
	private String max_limit;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getbId() {
		return bId;
	}

	public void setbId(Long bId) {
		this.bId = bId;
	}

	public String getMin_limit() {
		return min_limit;
	}

	public void setMin_limit(String min_limit) {
		this.min_limit = min_limit;
	}

	public String getMax_limit() {
		return max_limit;
	}

	public void setMax_limit(String max_limit) {
		this.max_limit = max_limit;
	}

	public String getPayment_channel() {
		return payment_channel;
	}

	public void setPayment_channel(String payment_channel) {
		this.payment_channel = payment_channel;
	}
	
	
}
