package com.etpl.bbps.model;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "TT_bill_Avenue_Biller")
@XmlRootElement(name = "biller")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillAvenueBillerInfo {

    @XmlElement(name = "responseCode")
    private String responseCode;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "biller_id")
    @XmlElement(name = "billerId")
    private String billerId;

    @Column(name = "biller_name")
    @XmlElement(name = "billerName")
    private String billerName;

    @Column(name = "billerCategory")
    @XmlElement(name = "billerCategory")
    private String billerCategory;

    @Column(name = "billerAdhoc")
    @XmlElement(name = "billerAdhoc")
    private String billerAdhoc;

    @Column(name = "biller_coverage")
    @XmlElement(name = "billerCoverage")
    private String billerCoverage;

    @Column(name = "biller_fetch_requirement")
    @XmlElement(name = "billerFetchRequiremet")
    private String billerFetchRequirement;

    @Column(name = "biller_payment_exactness")
    @XmlElement(name = "billerPaymentExactness")
    private String billerPaymentExactness;

    @Column(name = "biller_support_bill_validation")
    @XmlElement(name = "billerSupportBillValidation")
    private String billerSupportBillValidation;

    @Column(name = "support_pending_status")
    @XmlElement(name = "supportPendingStatus")
    private String supportPendingStatus;

    @Column(name = "support_demand")
    @XmlElement(name = "supportDeemed")
    private String supportDemand;

    @Column(name = "biller_timeout")
    @XmlElement(name = "billerTimeout")
    private String billerTimeout;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "biller_id", referencedColumnName = "biller_id")
    @XmlElementWrapper(name = "billerInputParams")
    @XmlElement(name = "paramInfo")
    private List<BillAvenueBillerInputParams> billerInputParams;

    @Column(name = "biller_additional_info")
    @XmlElement(name = "billerAdditionalInfo")
    private String billerAdditionalInfo;

    @Column(name = "biller_amount_options")
    @XmlElement(name = "billerAmountOptions")
    private String billerAmountOptions;

    @Column(name = "biller_payment_modes")
    @XmlElement(name = "billerPaymentModes")
    private String billerPaymentModes;

    @Column(name = "biller_description")
    @XmlElement(name = "billerDescription")
    private String billerDescription;

    @Column(name = "recharge_amount_in_validation_request")
    @XmlElement(name = "rechargeAmountInValidationRequest")
    private String rechargeAmountInValidationRequest;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "biller_id", referencedColumnName = "biller_id")
    @XmlElementWrapper(name = "billerPaymentChannels")
    @XmlElement(name = "paymentChannelInfo")
    private List<BillAvenueBillerPaymentChannel> billerPaymentChannels;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }



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

	public String getBillerAdhoc() {
		return billerAdhoc;
	}

	public void setBillerAdhoc(String billerAdhoc) {
		this.billerAdhoc = billerAdhoc;
	}

	public String getBillerCoverage() {
		return billerCoverage;
	}

	public void setBillerCoverage(String billerCoverage) {
		this.billerCoverage = billerCoverage;
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

	public String getSupportPendingStatus() {
		return supportPendingStatus;
	}

	public void setSupportPendingStatus(String supportPendingStatus) {
		this.supportPendingStatus = supportPendingStatus;
	}

	public String getSupportDemand() {
		return supportDemand;
	}

	public void setSupportDemand(String supportDemand) {
		this.supportDemand = supportDemand;
	}

	public String getBillerTimeout() {
		return billerTimeout;
	}

	public void setBillerTimeout(String billerTimeout) {
		this.billerTimeout = billerTimeout;
	}

	public List<BillAvenueBillerInputParams> getBillerInputParams() {
		return billerInputParams;
	}

	public void setBillerInputParams(List<BillAvenueBillerInputParams> billerInputParams) {
		this.billerInputParams = billerInputParams;
	}

	public String getBillerAdditionalInfo() {
		return billerAdditionalInfo;
	}

	public void setBillerAdditionalInfo(String billerAdditionalInfo) {
		this.billerAdditionalInfo = billerAdditionalInfo;
	}

	public String getBillerAmountOptions() {
		return billerAmountOptions;
	}

	public void setBillerAmountOptions(String billerAmountOptions) {
		this.billerAmountOptions = billerAmountOptions;
	}

	public String getBillerPaymentModes() {
		return billerPaymentModes;
	}

	public void setBillerPaymentModes(String billerPaymentModes) {
		this.billerPaymentModes = billerPaymentModes;
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

	public List<BillAvenueBillerPaymentChannel> getBillerPaymentChannels() {
		return billerPaymentChannels;
	}

	public void setBillerPaymentChannels(List<BillAvenueBillerPaymentChannel> billerPaymentChannels) {
		this.billerPaymentChannels = billerPaymentChannels;
	}

	
	
	
}
