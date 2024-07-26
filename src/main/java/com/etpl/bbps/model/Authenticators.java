package com.etpl.bbps.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Authenticators")
@Table(name = "tl_authenticators")
public class Authenticators {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "AU_ID_FK")
	  private Long bId;
	
	@Column(name = "PARAMETER_NAME")
	private String parameter_name;
	
	@Column(name = "DATA_TYPE")
	private String data_type;
	
	@Column(name = "OPTIONAL")
	private String optional;
	
	@Column(name = "REGEX",columnDefinition = "NVARCHAR(2000)")
	private String regex;
	
	@Column(name = "ERROR_MESSAGE")
	private String error_message;
	
	@Column(name = "SEQ")
	private String seq;
	
	@Column(name = "ENCRYPTION_REQUIRED")
	private String encryption_required;
	
	@Column(name = "USER_INPUT")
	private String user_input;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getbId() {
		return bId;
	}

	public void setbId(Long bId) {
		this.bId = bId;
	}

	public String getParameter_name() {
		return parameter_name;
	}

	public void setParameter_name(String parameter_name) {
		this.parameter_name = parameter_name;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getOptional() {
		return optional;
	}

	public void setOptional(String optional) {
		this.optional = optional;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getEncryption_required() {
		return encryption_required;
	}

	public void setEncryption_required(String encryption_required) {
		this.encryption_required = encryption_required;
	}

	public String getUser_input() {
		return user_input;
	}

	public void setUser_input(String user_input) {
		this.user_input = user_input;
	}

	
	
}
