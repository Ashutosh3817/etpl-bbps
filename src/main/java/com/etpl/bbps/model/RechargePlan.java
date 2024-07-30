package com.etpl.bbps.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="recharge_plan")
public class RechargePlan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToMany(mappedBy="rechargePlan",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<RechargePlanDetails> rechargePlanDetails;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<RechargePlanDetails> getRechargePlanDetails() {
		return rechargePlanDetails;
	}

	public void setRechargePlanDetails(List<RechargePlanDetails> rechargePlanDetails) {
		this.rechargePlanDetails = rechargePlanDetails;
	}
	
	

}
