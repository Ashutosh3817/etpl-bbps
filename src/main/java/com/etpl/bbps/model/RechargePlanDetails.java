package com.etpl.bbps.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.checkerframework.checker.interning.qual.InternedDistinct;

@Entity
@Table(name="recharge_plan_details")
public class RechargePlanDetails {
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recharge_plan_id")
	private RechargePlan rechargePlan;
	
	@Column(name="amount")
	private String amount;
	
	@Column(name="description")
	private String description;
	
	@Column(name="location_name")
	private String locationName;
	
	@Column(name="plan_name")
	private String planName;
	
	@Column(name="service_provider_name")
	private String ServiceProviderName;
	
	@Column(name="talktime")
	private String talktime;
	
	@Column(name="validity")
	private String validity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public RechargePlan getRechargePlan() {
		return rechargePlan;
	}

	public void setRechargePlan(RechargePlan rechargePlan) {
		this.rechargePlan = rechargePlan;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getServiceProviderName() {
		return ServiceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName) {
		ServiceProviderName = serviceProviderName;
	}

	public String getTalktime() {
		return talktime;
	}

	public void setTalktime(String talktime) {
		this.talktime = talktime;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}
	
}
