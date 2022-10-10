package com.example.demo.api.response;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import com.example.demo.model.OperatorCode;
import com.example.demo.model.SIMStatusType;

import io.swagger.annotations.ApiModelProperty;

public class SIMDTO {
	
	public interface SIMDTOCreation {
	}
	
	public interface SIMDTOUpdate {
		
	}
	
	@ApiModelProperty(example = "1001", notes = "if doesn't exists is automatically created")
	private Long id;
	
	@NotNull(groups = { SIMDTO.SIMDTOCreation.class, SIMDTO.SIMDTOUpdate.class })
	@ApiModelProperty(example = "VOD / ORA / JAZ", notes = "short code of operator companies")
	private OperatorCode operatorCode;
	
	@NotNull(groups = { SIMDTO.SIMDTOCreation.class, SIMDTO.SIMDTOUpdate.class })
	@ApiModelProperty(example = "ESP / ARG / UK", notes = "iso code of countries")
	private String country;
	
	@NotNull(groups = { SIMDTO.SIMDTOCreation.class, SIMDTO.SIMDTOUpdate.class })
	@Column(name = "simId", unique = true)
	@ApiModelProperty(example = "10333222")
	private String simId;
	
	@NotNull(groups = { SIMDTO.SIMDTOCreation.class, SIMDTO.SIMDTOUpdate.class })
	@ApiModelProperty(example = "ACTIVE / WAITING_FOR_ACTIVATION / BLOCKED")
	private SIMStatusType status;

	public OperatorCode getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(OperatorCode operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public SIMStatusType getStatus() {
		return status;
	}

	public void setStatus(SIMStatusType status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

	

}
