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


import javax.persistence.*;

@Entity
@Table(name = "biller_input_params")
public class BillerInputParams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biller_id") 
    private BillerInfo billerInfo;

    @Column(name = "param_name")
    private String paramName;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "is_optional")
    private boolean isOptional;

    @Column(name = "min_length")
    private int minLength;

    @Column(name = "max_length")
    private int maxLength;

    @Column(name = "reg_ex")
    private String regEx;



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BillerInfo getBillerInfo() {
		return billerInfo;
	}

	public void setBillerInfo(BillerInfo billerInfo) {
		this.billerInfo = billerInfo;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public boolean isOptional() {
		return isOptional;
	}

	public void setOptional(boolean isOptional) {
		this.isOptional = isOptional;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public String getRegEx() {
		return regEx;
	}

	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}

	
}
