package com.etpl.bbps.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="BillAvenueBillerInputParams")
@Table(name = "TL_biller_Input_Param")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillAvenueBillerInputParams {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "biller_id")
    private BillAvenueBillerInfo billerInfo;

    @Column(name = "param_name")
    @XmlElement(name = "paramName")
    private String paramName;

    @Column(name = "data_type")
    @XmlElement(name = "dataType")
    private String dataType;

    @Column(name = "is_optional")
    @XmlElement(name = "isOptional")
    private Boolean isOptional;

    @Column(name = "min_length")
    @XmlElement(name = "minLength")
    private Integer minLength;

    @Column(name = "max_length")
    @XmlElement(name = "maxLength")
    private Integer maxLength;

    @Column(name = "visibility")
    @XmlElement(name = "visibility")
    private Boolean visibility;

    // Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getIsOptional() {
		return isOptional;
	}

	public void setIsOptional(Boolean isOptional) {
		this.isOptional = isOptional;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Boolean getVisibility() {
		return visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}
    
    
    
}