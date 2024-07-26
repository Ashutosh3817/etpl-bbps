package com.etpl.bbps.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "BbpsTransaction")
@Table(name = "tl_bbps_transaction")
public class BbpsTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "SOURCE_ID")
	private String sourceId;
	
	@Column(name = "CUSTOMER_ID")
	private String customerId;
	
	@Column(name = "CUSTOMER_MOBILE")
	private String customerMobile;
	
	@Column(name = "PAYMENT_ID")
	private String paymentId;
	
	@Column(name = "OBJECT_ID")
	private String objectId;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "BILLER_ID")
	private String billerId;
	
	@Column(name = "SOURCE_REF_NO")
	private String sourceRefNo;
	
	@Column(name = "PAYMENT_AMOUNT")
	private String paymentAmount;
	
	@Column(name = "PAYMENT_REMARKS")
	private String paymentRemarks;	
	
	@Column(name = "BILLER_NAME")
	private String billerName;	
	
	@Column(name = "BILLER_CATEGORY")
	private String billerCategory;	
	
	@Column(name = "PAYMENT_TYPE")
	private String paymentType;	
	
	@Column(name = "TXN_DATE_TIME")
	private String txnDateTime;	
	
	@Column(name = "BILLER_STATUS")
	private String billerStatus;		
	
	@Column(name = "PAYMENT_METHOD")
	private String paymentMethod;
	
	@Column(name = "VPA")
	private String vpa;	
	
	@Column(name = "PAYMENT_STATUS")
	private String paymentStatus;
	
	@Column(name = "BBPS_REF_NO")
	private String bbpsRefNo;
	
	@Column(name = "AUTHENTICATORS",length = 5000)
	private String authenticators;
	
	@Column(name = "ADDITIONAL_INFO",length = 5000)
	private String additionalInfo;
	
	@Column(name = "BILL_LIST",length = 5000)
	private String billList;
	
	@Column(name = "BILLER_APPROVAL_CODE")
	private String billerApprovalCode;
	
	@Column(name = "COU_CONV_FEE")
	private String couConvFee;
	
	@Column(name = "BOU_CONV_FEE")
	private String bouConvFee;
	
	@Column(name = "BILLER_CAT_RES")
	private String billerCatRes;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_ON")
	private Calendar createdOn;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_ON")
	private java.util.Calendar updatedOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

	public String getSourceRefNo() {
		return sourceRefNo;
	}

	public void setSourceRefNo(String sourceRefNo) {
		this.sourceRefNo = sourceRefNo;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentRemarks() {
		return paymentRemarks;
	}

	public void setPaymentRemarks(String paymentRemarks) {
		this.paymentRemarks = paymentRemarks;
	}

	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	public String getBillerCategory() {
		return billerCategory;
	}

	public void setBillerCategory(String billerCategory) {
		this.billerCategory = billerCategory;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getTxnDateTime() {
		return txnDateTime;
	}

	public void setTxnDateTime(String txnDateTime) {
		this.txnDateTime = txnDateTime;
	}

	public String getBillerStatus() {
		return billerStatus;
	}

	public void setBillerStatus(String billerStatus) {
		this.billerStatus = billerStatus;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getBbpsRefNo() {
		return bbpsRefNo;
	}

	public void setBbpsRefNo(String bbpsRefNo) {
		this.bbpsRefNo = bbpsRefNo;
	}

	public Calendar getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}

	public java.util.Calendar getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(java.util.Calendar updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getAuthenticators() {
		return authenticators;
	}

	public void setAuthenticators(String authenticators) {
		this.authenticators = authenticators;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getBillerApprovalCode() {
		return billerApprovalCode;
	}

	public void setBillerApprovalCode(String billerApprovalCode) {
		this.billerApprovalCode = billerApprovalCode;
	}

	public String getCouConvFee() {
		return couConvFee;
	}

	public void setCouConvFee(String couConvFee) {
		this.couConvFee = couConvFee;
	}

	public String getBouConvFee() {
		return bouConvFee;
	}

	public void setBouConvFee(String bouConvFee) {
		this.bouConvFee = bouConvFee;
	}

	public String getBillList() {
		return billList;
	}

	public void setBillList(String billList) {
		this.billList = billList;
	}

	public String getBillerCatRes() {
		return billerCatRes;
	}

	public void setBillerCatRes(String billerCatRes) {
		this.billerCatRes = billerCatRes;
	}
	
	
	
}
