package com.etpl.bbps.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.etpl.bbps.model.dto.BillerInfoResponseDTO.BillerInfoParamsDto;

@Entity
@Table(name="biller_info")
public class BillerInfo {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="biller_id",nullable=false,unique=true)
	private String billerId;
	
	@Column(name="biller_name")
	private String billerName;
	
	
	@Column(name="biller_category")
	private String billerCategory;
	
	@Column(name="biller_adhoc")
	private boolean billerAdhoc;
	
	@Column(name="biller_coverage")
	private String billerCoverage;
	
	@Column(name="biller_fetch_requirement")
	private String billerFetchRequirement;
	
	@Column(name="biller_payment_exactness")
	private String billerPaymentExactness;
	
	@Column(name="biller_support_bill_validation")
	private String billerSupportBillValidation;
	
	@Column(name="biller_amount_options")
	private String billerAmountOptions;
	
	@Column(name="biller_payment_models", columnDefinition="TEXT")
	private List<String> billerPaymentModels;
	
	@OneToMany(mappedBy = "biller_info",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<BillerInputParams> billerInputParams;
	
	@Column(name="biller_description")
	private String billerDescription;
	
	@Column(name="recharge_amount_in_validation_request")
	private String rechargeAmountInValidationRequest;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
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

	public boolean isBillerAdhoc() {
		return billerAdhoc;
	}

	public void setBillerAdhoc(boolean billerAdhoc) {
		this.billerAdhoc = billerAdhoc;
	}

	public String getBillerFetchRequirement() {
		return billerFetchRequirement;
	}

	public void setBillerFetchRequirement(String billerFetchRequirement) {
		this.billerFetchRequirement = billerFetchRequirement;
	}

	public String getBillerPaymentExactness() {
		return billerPaymentExactness;
	}

	public void setBillerPaymentExactness(String billerPaymentExactness) {
		this.billerPaymentExactness = billerPaymentExactness;
	}

	public String getBillerSupportBillValidation() {
		return billerSupportBillValidation;
	}

	public void setBillerSupportBillValidation(String billerSupportBillValidation) {
		this.billerSupportBillValidation = billerSupportBillValidation;
	}

	public String getBillerAmountOptions() {
		return billerAmountOptions;
	}

	public void setBillerAmountOptions(String billerAmountOptions) {
		this.billerAmountOptions = billerAmountOptions;
	}

	public List<String> getBillerPaymentModels() {
		return billerPaymentModels;
	}

	public void setBillerPaymentModels(List<String> billerPaymentModels) {
		this.billerPaymentModels = billerPaymentModels;
	}

	public List<BillerInputParams> getBillerInputParams() {
		return billerInputParams;
	}

	public void setBillerInputParams(List<BillerInputParams> billerInputParams) {
		this.billerInputParams = billerInputParams;
	}

	public String getBillerDescription() {
		return billerDescription;
	}

	public void setBillerDescription(String billerDescription) {
		this.billerDescription = billerDescription;
	}

	public String getRechargeAmountInValidationRequest() {
		return rechargeAmountInValidationRequest;
	}

	public void setRechargeAmountInValidationRequest(String rechargeAmountInValidationRequest) {
		this.rechargeAmountInValidationRequest = rechargeAmountInValidationRequest;
	}

	public String getBillerCoverage() {
		return billerCoverage;
	}

	public void setBillerCoverage(String billerCoverage) {
		this.billerCoverage = billerCoverage;
	}


	}
	
	
