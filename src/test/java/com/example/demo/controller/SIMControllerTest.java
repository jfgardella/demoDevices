package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.api.response.SIMDTO;
import com.example.demo.model.OperatorCode;
import com.example.demo.model.SIM;
import com.example.demo.model.SIMStatusType;
import com.example.demo.repository.ISimRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class SIMControllerTest {

	@Autowired
    private MockMvc mvc;
	
	 @Autowired
	  private ObjectMapper objectMapper;
	 
	 @Autowired
	 private ISimRepository simRepository;
	

	 @Test
		void getSIMTest() throws JsonProcessingException, Exception {
			SIMDTO simDTO = new SIMDTO();
			simDTO.setCountry("ARG");
			simDTO.setOperatorCode(OperatorCode.VOD);
			simDTO.setSimId("1000");
			simDTO.setStatus(SIMStatusType.WAITING_FOR_ACTIVATION);
			mvc.perform(post("/v1/sims")
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(simDTO)))
		            .andExpect(status().isOk());
			SIM sim = simRepository.findAll().stream().filter(x -> x.getSimId().equals(simDTO.getSimId())).findFirst().get();	
;			mvc.perform(get("/v1/sims/{id}", sim.getId())
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(simDTO)))
		            .andExpect(status().isOk());
		}	 
	 
	 @Test
		void getSIMWithExceptionTest() throws JsonProcessingException, Exception {
			mvc.perform(get("/v1/sims/{id}", "3003")
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(new SIMDTO())))
		            .andExpect(status().isInternalServerError());
		}
	 
		@Test
		void creatSIMTest() throws JsonProcessingException, Exception {
			SIMDTO simDTO = new SIMDTO();
			simDTO.setCountry("ARG");
			simDTO.setOperatorCode(OperatorCode.VOD);
			simDTO.setSimId("1001");
			simDTO.setStatus(SIMStatusType.WAITING_FOR_ACTIVATION);
			mvc.perform(post("/v1/sims")
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(simDTO)))
		            .andExpect(status().isOk());
		}
		
		@Test
		void creatSIMWithConflictTest() throws JsonProcessingException, Exception {
			SIMDTO simDTO = new SIMDTO();
			simDTO.setCountry("ARG");
			simDTO.setOperatorCode(OperatorCode.VOD);
			simDTO.setSimId("1002");
			simDTO.setStatus(SIMStatusType.WAITING_FOR_ACTIVATION);
			mvc.perform(post("/v1/sims")
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(simDTO)))
		            .andExpect(status().isOk());
			mvc.perform(post("/v1/sims")
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(simDTO)))
		            .andExpect(status().isConflict());
		}
		
		@Test
		void creatSIMWithBadRequestExceptionTest() throws JsonProcessingException, Exception {
			SIMDTO simDTO = new SIMDTO();
			simDTO.setStatus(SIMStatusType.WAITING_FOR_ACTIVATION);
			mvc.perform(post("/v1/sims")
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(null)))
		            .andExpect(status().isBadRequest());
		}
		
		@Test
		void updateSIMTest() throws JsonProcessingException, Exception {
			SIMDTO simDTO = new SIMDTO();
			simDTO.setCountry("ARG");
			simDTO.setOperatorCode(OperatorCode.VOD);
			simDTO.setSimId("1003");
			simDTO.setStatus(SIMStatusType.WAITING_FOR_ACTIVATION);
			mvc.perform(post("/v1/sims")
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(simDTO)))
		            .andExpect(status().isOk());
			SIM sim = simRepository.findAll().stream().filter(x -> x.getSimId().equals(simDTO.getSimId())).findFirst().get();	

			sim.setStatus(SIMStatusType.ACTIVE);
			mvc.perform(put("/v1/sims/{id}", sim.getId())
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(sim)))
		            .andExpect(status().isOk());
		}
		
		@Test
		void updateSIMWithConflictTest() throws JsonProcessingException, Exception {
			SIMDTO simDTO = new SIMDTO();
			simDTO.setCountry("ARG");
			simDTO.setOperatorCode(OperatorCode.VOD);
			simDTO.setSimId("1004");
			simDTO.setStatus(SIMStatusType.WAITING_FOR_ACTIVATION);
			mvc.perform(put("/v1/sims/{id}", "3004")
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(simDTO)))
		            .andExpect(status().isConflict());
		}
		
		@Test
		void deleteSIMTest() throws JsonProcessingException, Exception {
			SIMDTO simDTO = new SIMDTO();
			simDTO.setCountry("ARG");
			simDTO.setOperatorCode(OperatorCode.VOD);
			simDTO.setSimId("1004");
			simDTO.setStatus(SIMStatusType.WAITING_FOR_ACTIVATION);
			mvc.perform(post("/v1/sims")
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(simDTO)))
		            .andExpect(status().isOk());
			SIM sim = simRepository.findAll().get(0);
			sim.setStatus(SIMStatusType.ACTIVE);
			mvc.perform(delete("/v1/sims/{id}", sim.getId())
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(simDTO)))
		            .andExpect(status().isOk());
		}
		
		@Test
		void deleteSIMWithExceptionTest() throws JsonProcessingException, Exception {
			mvc.perform(delete("/v1/sims/{id}", "2002")
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(null)))
		            .andExpect(status().isInternalServerError());
		}
}
