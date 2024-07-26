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

@Entity(name = "Complaint")
@Table(name = "tl_bbps_complaint")
public class Complaint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "OBJECT_ID")
	private String objectId;
	
	@Column(name = "COMPLAINT_ID")
	private String complaintId;
	
	@Column(name = "BILLER_ID")
	private String billerId;
	
	@Column(name = "CUSTOMER_MOBILE")
	private String customerMobile;
	
	@Column(name = "COMPLAINT_DATE")
	private String complaintDate;
	
	@Column(name = "COMPLAINT_TYPE")
	private String complaintType;
	
	@Column(name = "PARTICIPATION_TYPE")
	private String participationType;
	
	@Column(name = "AGENT_ID")
	private String agentId;
	
	@Column(name = "COMPLAINT_REASON")
	private String complaintReason;	
	
	@Column(name = "DISPOSITION")
	private String disposition;	
	
	@Column(name = "COMPLAINT_DESC")
	private String complaintDesc;	
	
	@Column(name = "COMPLAINT_STATUS")
	private String complaintStatus;	
	
	@Column(name = "COMPLAINT_REMARKS")
	private String complaintRemarks;	
	
	@Column(name = "ASSIGNED")
	private String assigned;		
	
	@Column(name = "RESPONSE_STATUS")
	private String responseStatus;
	
	@Column(name = "RESPONSE_DESCRIPTION")
	private String responseDescription;	
	
	@Column(name = "IS_BILLER_BBPS")
	private String isBillerBbps;
	
	@Column(name = "BBPS_REF_NO")
	private String bbpsRefNo;
	
	@Column(name = "PAYMENT_ID")
	private String paymentId;
	
	@Column(name = "SOURCE_REF_NO")
	private String sourceRefNo;
	
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

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(String complaintDate) {
		this.complaintDate = complaintDate;
	}

	public String getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}

	public String getParticipationType() {
		return participationType;
	}

	public void setParticipationType(String participationType) {
		this.participationType = participationType;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getComplaintReason() {
		return complaintReason;
	}

	public void setComplaintReason(String complaintReason) {
		this.complaintReason = complaintReason;
	}

	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public String getComplaintDesc() {
		return complaintDesc;
	}

	public void setComplaintDesc(String complaintDesc) {
		this.complaintDesc = complaintDesc;
	}

	public String getComplaintStatus() {
		return complaintStatus;
	}

	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	public String getComplaintRemarks() {
		return complaintRemarks;
	}

	public void setComplaintRemarks(String complaintRemarks) {
		this.complaintRemarks = complaintRemarks;
	}

	public String getAssigned() {
		return assigned;
	}

	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public String getIsBillerBbps() {
		return isBillerBbps;
	}

	public void setIsBillerBbps(String isBillerBbps) {
		this.isBillerBbps = isBillerBbps;
	}

	public String getBbpsRefNo() {
		return bbpsRefNo;
	}

	public void setBbpsRefNo(String bbpsRefNo) {
		this.bbpsRefNo = bbpsRefNo;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getSourceRefNo() {
		return sourceRefNo;
	}

	public void setSourceRefNo(String sourceRefNo) {
		this.sourceRefNo = sourceRefNo;
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

	
	
	
}
