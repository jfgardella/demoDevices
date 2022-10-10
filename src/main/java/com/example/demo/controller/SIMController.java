package com.example.demo.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.response.SIMDTO;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.ISimService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/v1/sims", produces = "application/json")
@ApiOperation("Sims API")
public class SIMController {

	@Autowired
	private ISimService simService;

	@Autowired
	private Logger logger;

	@ApiOperation(value = "Create a SIM")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK. The SIM was created", response = SIMDTO.class),
			@ApiResponse(code = 400, message = "bad request. You have mandatory fields"),
			@ApiResponse(code = 409, message = "Conflict. The SIM already exists"),
			@ApiResponse(code = 500, message = "Internal Error") })
	@PostMapping()
	public SIMDTO createSIM(@RequestBody @Validated(SIMDTO.SIMDTOCreation.class) SIMDTO sim)
			throws BadRequestException, ConflictException, InternalServerException {
		try {
			return simService.save(sim);
			
		} catch (ConflictException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Unexpected error on createSIM");
			throw new InternalServerException(ex.getMessage());
		}

	}

	@ApiOperation(value = "Configure a SIM")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK. SIM saved", response = SIMDTO.class),
			@ApiResponse(code = 400, message = "bad request. You have mandatory fields"),
			@ApiResponse(code = 409, message = "Conflict. Something went wrong with the sim that are you tring to update"),
			@ApiResponse(code = 500, message = "Internal Error") })
	@PutMapping("/{id}")
	public SIMDTO updateSIM(@PathVariable Long id, @RequestBody @Validated(SIMDTO.SIMDTOUpdate.class) SIMDTO simDTO)
			throws BadRequestException, ConflictException, InternalServerException {
		try {
			return simService.updateSIM(simDTO);
			
		} catch (ConflictException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Unexpected error on updateSIM");
			throw new InternalServerException(ex.getMessage());
		}
	}

	@ApiOperation(value = "Retrieve an specific SIM")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK. return a SIM", response = SIMDTO.class),
			@ApiResponse(code = 400, message = "bad request. You have mandatory fields"),
			@ApiResponse(code = 500, message = "Internal Error") })
	@GetMapping("/{id}")
	public SIMDTO retrieveSpecificsSIM(@PathVariable Long id) throws InternalServerException, NotFoundException {
		try {
			return simService.getSIM(id);
			
		} catch (Exception ex) {
			logger.error("Unexpected error on retrieveSpecificSIM");
			throw new InternalServerException(ex.getMessage());
		}
	}

	@ApiOperation(value = "delete an specific SIM")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK. The sim was deleted"),
			@ApiResponse(code = 400, message = "bad request. The id is mandatory"),
			@ApiResponse(code = 500, message = "Internal Error.") })
	@DeleteMapping("/{id}")
	public void deleteAnSpecificSim(@PathVariable Long id) throws InternalServerException {
		try {
			simService.deleteSIM(id);
			
		} catch (Exception ex) {
			throw new InternalServerException(ex.getMessage());
		}
	}

}
