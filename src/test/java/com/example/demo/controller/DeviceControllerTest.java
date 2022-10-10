package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.api.response.DeviceDTO;
import com.example.demo.api.response.SIMDTO;
import com.example.demo.model.Device;
import com.example.demo.model.OperatorCode;
import com.example.demo.model.SIMStatusType;
import com.example.demo.repository.IDeviceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class DeviceControllerTest {

	@Autowired
    private MockMvc mvc;
	
	 @Autowired
	  private ObjectMapper objectMapper;
	 
	 @Autowired
	 private IDeviceRepository deviceRepository;
	
	
	@Test
	void createDeviceTest() throws JsonProcessingException, Exception {
		DeviceDTO deviceDTO = new DeviceDTO();
		deviceDTO.setSerialNumber("1001");
		mvc.perform(post("/v1/devices")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(deviceDTO)))
	            .andExpect(status().isOk());
		//.param("sendWelcomeMail", "true")
		Device device = deviceRepository.findAll().get(0);
		Assertions.assertThat("1001".equals(device.getSerialNumber()));
	}
	
	@Test
	void createDeviceWithConflictExceptionTest() throws JsonProcessingException, Exception {
		DeviceDTO deviceDTO = new DeviceDTO();
		deviceDTO.setSerialNumber("1002");
		mvc.perform(post("/v1/devices")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(deviceDTO)))
	            .andExpect(status().isOk());
		Device device = deviceRepository.findAll().get(0);
		Assertions.assertThat("1001".equals(device.getSerialNumber()));
		
		
		mvc.perform(post("/v1/devices")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(deviceDTO)))
	            .andExpect(status().isConflict());
	}
	
	@Test
	void createDeviceWithBadRequestExceptionTest() throws JsonProcessingException, Exception {
		DeviceDTO deviceDTO = new DeviceDTO();
		mvc.perform(post("/v1/devices")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(deviceDTO)))
	            .andExpect(status().isBadRequest());
	
	}
	
	@Test
	void createDeviceWithSIMTest() throws JsonProcessingException, Exception {
		DeviceDTO deviceDTO = new DeviceDTO();
		deviceDTO.setSerialNumber("1003");
		SIMDTO simDTO = new SIMDTO();
		simDTO.setCountry("ARG");
		simDTO.setOperatorCode(OperatorCode.VOD);
		simDTO.setStatus(SIMStatusType.WAITING_FOR_ACTIVATION);
		simDTO.setSimId("30003");
		deviceDTO.setSim(simDTO);
		mvc.perform(post("/v1/devices")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(deviceDTO)))
	            .andExpect(status().isOk());
	
	}
	
	@Test
	void getOneDeviceTest() throws JsonProcessingException, Exception {
		DeviceDTO deviceDTO = new DeviceDTO();
		deviceDTO.setSerialNumber("1004");
		mvc.perform(post("/v1/devices")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(deviceDTO)))
	            .andExpect(status().isOk());
		//.param("sendWelcomeMail", "true")
		Device device = deviceRepository.findAll().get(0);
		
		mvc.perform(get("/v1/devices/{id}", device.getId())
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(deviceDTO)))
	            .andExpect(status().isOk());
	}
	
	@Test
	void getOneDeviceWithExceptionTest() throws JsonProcessingException, Exception {	
		mvc.perform(get("/v1/devices/{id}", " ")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(null)))
	            .andExpect(status().isInternalServerError());
	}
	
	@Test
	void updateOneDeviceTest() throws JsonProcessingException, Exception {
		DeviceDTO deviceDTO = new DeviceDTO();
		deviceDTO.setSerialNumber("1005");
		mvc.perform(post("/v1/devices")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(deviceDTO)))
	            .andExpect(status().isOk());
		Device device = deviceRepository.findAll().stream().filter(x -> x.getSerialNumber().equals(deviceDTO.getSerialNumber())).findFirst().get();
		device.setPrice(Double.valueOf("23"));
		mvc.perform(put("/v1/devices/{id}", device.getId())
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(device)))
	            .andExpect(status().isOk());
	}
	
	
	@Test
	void deleteDeviceTest() throws JsonProcessingException, Exception {
		DeviceDTO deviceDTO = new DeviceDTO();
		deviceDTO.setSerialNumber("1006");
		mvc.perform(post("/v1/devices")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(deviceDTO)))
	            .andExpect(status().isOk());
		Device device = deviceRepository.findAll().stream().filter(x -> x.getSerialNumber().equals(deviceDTO.getSerialNumber())).findFirst().get();
		mvc.perform(delete("/v1/devices/{id}", device.getId())
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(null)))
	            .andExpect(status().isOk());
	}
	
	@Test
	void deleteDeviceWithExceptionTest() throws JsonProcessingException, Exception {
		mvc.perform(delete("/v1/devices/{id}", "5005")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(null)))
	            .andExpect(status().isInternalServerError());
	}

}
