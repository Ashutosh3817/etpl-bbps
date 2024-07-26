package com.etpl.bbps.config;

import java.io.Serializable;

public class TokenResponse implements Serializable{

	private static final long serialVersionUID = 1333222566734L;
	
	private String status;
	private String mobileNo;
	private String userRole;
	private String fullName;
	private String gender;
	private String state;
	private String city;
	private String emailId;
	private String dob;
	private String maritialStatus;
	private String aadharNo;
	private String panNo;
	private String gstNo;
	private String firmName;
	private String kycStatus;
	private String agentCategory;
	private String agentId;

	public TokenResponse()
	{
		
	}
	
	public TokenResponse(String status, String mobileNo, String userRole, String fullName, String gender,
			String state, String city, String emailId, String dob, String maritialStatus, String aadharNo,
			String panNo, String gstNo, String firmName, String kycStatus, String agentCategory, String agentId) {
		this.status = status;
        this.mobileNo=mobileNo;
        this.userRole=userRole;
        this.fullName=fullName;
        this.gender=gender;
        this.state=state;
        this.city=city;
        this.emailId=emailId;
        this.dob=dob;
        this.maritialStatus=maritialStatus;
        this.aadharNo=aadharNo;
        this.panNo=panNo;
        this.gstNo=gstNo;
        this.firmName=firmName;
        this.kycStatus=kycStatus;
        this.agentCategory=agentCategory;
        this.agentId=agentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getMaritialStatus() {
		return maritialStatus;
	}

	public void setMaritialStatus(String maritialStatus) {
		this.maritialStatus = maritialStatus;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getKycStatus() {
		return kycStatus;
	}

	public void setKycStatus(String kycStatus) {
		this.kycStatus = kycStatus;
	}

	public String getAgentCategory() {
		return agentCategory;
	}

	public void setAgentCategory(String agentCategory) {
		this.agentCategory = agentCategory;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	
	
}
