package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sims")
public class SIM {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "simId", unique = true)
	private String simId;

	@Column(name = "operatorCode")
	private OperatorCode operatorCode;

	@Column(name = "country")
	private String country;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private SIMStatusType status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

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
}
