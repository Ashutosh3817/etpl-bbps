package com.etpl.bbps.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "AllowedPaymentMethod")
@Table(name = "tl_allowed_payment_method")
public class AllowedPaymentMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "APM_ID_FK")
	private Long bId;

	@Column(name = "PAYMENT_METHOD")
	private String payment_method;

	@Column(name = "MIN_LIMIT")
	private String min_limit;

	@Column(name = "MAX_LIMIT")
	private String max_limit;

	@Column(name = "AUTOPAY_ALLOWED")
	private String autopay_allowed;

	@Column(name = "PAYLATER_ALLOWED")
	private String paylater_allowed;

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

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
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

	public String getAutopay_allowed() {
		return autopay_allowed;
	}

	public void setAutopay_allowed(String autopay_allowed) {
		this.autopay_allowed = autopay_allowed;
	}

	public String getPaylater_allowed() {
		return paylater_allowed;
	}

	public void setPaylater_allowed(String paylater_allowed) {
		this.paylater_allowed = paylater_allowed;
	}

}
