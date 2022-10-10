package com.example.demo.api.response;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class DeviceDTO {

	public interface DeviceDTOCreation {
	}
	
	public interface DeviceDTOUpdate {
		
	}

	@ApiModelProperty(hidden = true)
	private Long id;

	@NotNull(groups = { DeviceDTO.DeviceDTOCreation.class, DeviceDTO.DeviceDTOUpdate.class })
	@ApiModelProperty(example = "LS3456M", notes = "unique value")
	private String serialNumber;

	@ApiModelProperty(notes = "SIM card", reference = "SIMDTO")
	private SIMDTO sim;

	@ApiModelProperty(example = "299.99")
	private Double price;
	@ApiModelProperty(example = "-24.00 / 84.00")
	private Double temperature;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public SIMDTO getSim() {
		return sim;
	}

	public void setSim(SIMDTO sim) {
		this.sim = sim;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
}
