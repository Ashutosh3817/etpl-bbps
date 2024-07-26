package com.etpl.bbps.model.dto;

import java.io.Serializable;
import java.util.Calendar;

public class UserModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 13332225667L;


	private Long id;


	private String typeOfEmployment;

	
	private String empCategory;

	
	private String divCode;

	
	private String zoneCode;
	
	
	private String verticalCode;

	
	private String locCode;

	
	private String office;

	
	private String salutation;

	
	private String dsgCode;

	
	private String grdCode;

	
	private String dptCode;

	
	private String subDepCode;

	
	private String gender;

	
	private String city;

	
	private String state;

	
	private String maritalStatus;

	
	private String empCode;


	private String salCode;


	private String firstName;


	private String middleName;


	private String lastName;

	
	private String rmCode;

	
	private String hodCode;

	private String presentAddress;

	private String permanentAddress;

	private String contactNo;

	private String emgContactNo;

	private String dob;

	private String doj;

	private String dom;

	private String officialEmailId;

	private String personalEmailId;
	
	private String empStatus;

	private String username;

	private String password;
	
	private String createdBy;

	private String fullname;

	private String userrole;


	private Calendar createdOn;

	public UserModel() {
	}


	public UserModel(String username, String password, String fullname, String userrole) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.userrole = userrole;
	}


	public String getTypeOfEmployment() {
		return typeOfEmployment;
	}


	public void setTypeOfEmployment(String typeOfEmployment) {
		this.typeOfEmployment = typeOfEmployment;
	}


	public String getEmpCategory() {
		return empCategory;
	}


	public void setEmpCategory(String empCategory) {
		this.empCategory = empCategory;
	}


	public String getDivCode() {
		return divCode;
	}


	public void setDivCode(String divCode) {
		this.divCode = divCode;
	}


	public String getZoneCode() {
		return zoneCode;
	}


	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}


	public String getLocCode() {
		return locCode;
	}


	public void setLocCode(String locCode) {
		this.locCode = locCode;
	}


	public String getOffice() {
		return office;
	}


	public void setOffice(String office) {
		this.office = office;
	}


	public String getSalutation() {
		return salutation;
	}


	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}


	public String getDsgCode() {
		return dsgCode;
	}


	public void setDsgCode(String dsgCode) {
		this.dsgCode = dsgCode;
	}


	public String getGrdCode() {
		return grdCode;
	}


	public void setGrdCode(String grdCode) {
		this.grdCode = grdCode;
	}


	public String getDptCode() {
		return dptCode;
	}


	public void setDptCode(String dptCode) {
		this.dptCode = dptCode;
	}


	public String getSubDepCode() {
		return subDepCode;
	}


	public void setSubDepCode(String subDepCode) {
		this.subDepCode = subDepCode;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getMaritalStatus() {
		return maritalStatus;
	}


	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}


	public String getEmpCode() {
		return empCode;
	}


	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}


	public String getSalCode() {
		return salCode;
	}


	public void setSalCode(String salCode) {
		this.salCode = salCode;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getMiddleName() {
		return middleName;
	}


	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getRmCode() {
		return rmCode;
	}


	public void setRmCode(String rmCode) {
		this.rmCode = rmCode;
	}


	public String getHodCode() {
		return hodCode;
	}


	public void setHodCode(String hodCode) {
		this.hodCode = hodCode;
	}


	public String getPresentAddress() {
		return presentAddress;
	}


	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}


	public String getPermanentAddress() {
		return permanentAddress;
	}


	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}


	public String getContactNo() {
		return contactNo;
	}


	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}


	public String getEmgContactNo() {
		return emgContactNo;
	}


	public void setEmgContactNo(String emgContactNo) {
		this.emgContactNo = emgContactNo;
	}


	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}


	public String getDoj() {
		return doj;
	}


	public void setDoj(String doj) {
		this.doj = doj;
	}


	public String getDom() {
		return dom;
	}


	public void setDom(String dom) {
		this.dom = dom;
	}


	public String getOfficialEmailId() {
		return officialEmailId;
	}


	public void setOfficialEmailId(String officialEmailId) {
		this.officialEmailId = officialEmailId;
	}


	public String getPersonalEmailId() {
		return personalEmailId;
	}


	public void setPersonalEmailId(String personalEmailId) {
		this.personalEmailId = personalEmailId;
	}


	public String getEmpStatus() {
		return empStatus;
	}


	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getFullname() {
		return fullname;
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
	}


	public String getUserrole() {
		return userrole;
	}


	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}


	public Calendar getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}


	public String getVerticalCode() {
		return verticalCode;
	}


	public void setVerticalCode(String verticalCode) {
		this.verticalCode = verticalCode;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	
	
}
