package com.etpl.bbps.model;

import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "BillerModel")
@Table(name = "tt_biller")
public class BillerModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "OBJECT_ID")
	private String objectid;
	
	@Column(name = "BILLER_ID")
	private String billerId;
	
	@Column(name = "BILLER_LEGAL_NAME")
	private String biller_legal_name;
	
	@Column(name = "BILLER_NAME")
	private String biller_name;
	
	@Column(name = "BILLER_LOCATIOIN")
	private String biller_location;
	
	@Column(name = "BILLER_CATEGORY")
	private String billerCategory;
	
	@Column(name = "BILLER_REG_ADDRESS", length=2000)
	private String biller_reg_address;
	
	@Column(name = "BILLER_REG_CITY")
	private String biller_reg_city;
	
	@Column(name = "BILLER_REG_PIN")
	private String biller_reg_pin;
	
	@Column(name = "BILLER_REG_STATE")
	private String biller_reg_state;
	
	@Column(name = "BILLER_REG_COUNTRY")
	private String biller_reg_country;
	
	@Column(name = "BILLER_MODE")
	private String biller_mode;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "APM_ID_FK")
	private List<AllowedPaymentMethod> allowedPaymentMethod;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "PC_ID_FK")
	private List<PaymentChannels> paymentChannels;
	
	@Column(name = "BILLER_STATUS")
	private String biller_status;
	
	@Column(name = "BILLER_CREATED_DATE")
	private String biller_created_date;
	
	@Column(name = "BILLER_LAST_MODIFIED_DATE")
	private String biller_lastmodified_date;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "AU_ID_FK")
	private List<Authenticators> authenticators;
	
	@Column(name = "BILLER_LOGO")
	private String biller_logo;
	
	@Column(name = "BILLER_BILL_COPY")
	private String biller_bill_copy;
	
	@Column(name = "BILLER_TYPE")
	private String biller_type;
	
	@Column(name = "PARTIAL_PAY")
	private String partial_pay;
	
	@Column(name = "PAY_AFTER_DUE_DATE")
	private String pay_after_duedate;
	
	@Column(name = "ONLINE_VALIDATION")
	private String online_validation;
	
	@Column(name = "IS_BILLER_BBPS")
	private String isbillerbbps;
	
	@Column(name = "PAYMENTAMOUNT_VALIDATION")
	private String paymentamount_validation;
	
	@Column(name = "BILL_PRESENTMENT")
	private String bill_presentment;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_ON")
	private Calendar createdOn;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_ON")
	private Calendar updatedOn;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObjectid() {
		return objectid;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}

	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

	public String getBiller_legal_name() {
		return biller_legal_name;
	}

	public void setBiller_legal_name(String biller_legal_name) {
		this.biller_legal_name = biller_legal_name;
	}

	public String getBiller_name() {
		return biller_name;
	}

	public void setBiller_name(String biller_name) {
		this.biller_name = biller_name;
	}

	public String getBiller_location() {
		return biller_location;
	}

	public void setBiller_location(String biller_location) {
		this.biller_location = biller_location;
	}

	public String getBiller_reg_address() {
		return biller_reg_address;
	}

	public void setBiller_reg_address(String biller_reg_address) {
		this.biller_reg_address = biller_reg_address;
	}

	public String getBiller_reg_city() {
		return biller_reg_city;
	}

	public void setBiller_reg_city(String biller_reg_city) {
		this.biller_reg_city = biller_reg_city;
	}

	public String getBiller_reg_pin() {
		return biller_reg_pin;
	}

	public void setBiller_reg_pin(String biller_reg_pin) {
		this.biller_reg_pin = biller_reg_pin;
	}

	public String getBiller_reg_state() {
		return biller_reg_state;
	}

	public void setBiller_reg_state(String biller_reg_state) {
		this.biller_reg_state = biller_reg_state;
	}

	public String getBiller_reg_country() {
		return biller_reg_country;
	}

	public void setBiller_reg_country(String biller_reg_country) {
		this.biller_reg_country = biller_reg_country;
	}

	public String getBiller_mode() {
		return biller_mode;
	}

	public void setBiller_mode(String biller_mode) {
		this.biller_mode = biller_mode;
	}

	public List<AllowedPaymentMethod> getAllowedPaymentMethod() {
		return allowedPaymentMethod;
	}

	public void setAllowedPaymentMethod(List<AllowedPaymentMethod> allowedPaymentMethod) {
		this.allowedPaymentMethod = allowedPaymentMethod;
	}

	public List<PaymentChannels> getPaymentChannels() {
		return paymentChannels;
	}

	public void setPaymentChannels(List<PaymentChannels> paymentChannels) {
		this.paymentChannels = paymentChannels;
	}

	public String getBiller_status() {
		return biller_status;
	}

	public void setBiller_status(String biller_status) {
		this.biller_status = biller_status;
	}

	public String getBiller_created_date() {
		return biller_created_date;
	}

	public void setBiller_created_date(String biller_created_date) {
		this.biller_created_date = biller_created_date;
	}

	public String getBiller_lastmodified_date() {
		return biller_lastmodified_date;
	}

	public void setBiller_lastmodified_date(String biller_lastmodified_date) {
		this.biller_lastmodified_date = biller_lastmodified_date;
	}

	public List<Authenticators> getAuthenticators() {
		return authenticators;
	}

	public void setAuthenticators(List<Authenticators> authenticators) {
		this.authenticators = authenticators;
	}

	public String getBiller_logo() {
		return biller_logo;
	}

	public void setBiller_logo(String biller_logo) {
		this.biller_logo = biller_logo;
	}

	public String getBiller_bill_copy() {
		return biller_bill_copy;
	}

	public void setBiller_bill_copy(String biller_bill_copy) {
		this.biller_bill_copy = biller_bill_copy;
	}

	public String getBiller_type() {
		return biller_type;
	}

	public void setBiller_type(String biller_type) {
		this.biller_type = biller_type;
	}

	public String getPartial_pay() {
		return partial_pay;
	}

	public void setPartial_pay(String partial_pay) {
		this.partial_pay = partial_pay;
	}

	public String getPay_after_duedate() {
		return pay_after_duedate;
	}

	public void setPay_after_duedate(String pay_after_duedate) {
		this.pay_after_duedate = pay_after_duedate;
	}

	public String getOnline_validation() {
		return online_validation;
	}

	public void setOnline_validation(String online_validation) {
		this.online_validation = online_validation;
	}

	public String getIsbillerbbps() {
		return isbillerbbps;
	}

	public void setIsbillerbbps(String isbillerbbps) {
		this.isbillerbbps = isbillerbbps;
	}

	public String getPaymentamount_validation() {
		return paymentamount_validation;
	}

	public void setPaymentamount_validation(String paymentamount_validation) {
		this.paymentamount_validation = paymentamount_validation;
	}

	public String getBill_presentment() {
		return bill_presentment;
	}

	public void setBill_presentment(String bill_presentment) {
		this.bill_presentment = bill_presentment;
	}

	public String getBillerCategory() {
		return billerCategory;
	}

	public void setBillerCategory(String billerCategory) {
		this.billerCategory = billerCategory;
	}

	public Calendar getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}

	public Calendar getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Calendar updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
}
