package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.response.DeviceDTO;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.model.SIMStatusType;
import com.example.demo.service.IDeviceService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/v1/devices", produces = "application/json")
@ApiOperation("Devices API")
public class DeviceController {

	@Autowired
	private IDeviceService deviceService;

	@Autowired
	private Logger logger;

	@ApiOperation(value = "Retrieve a list of devices")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. retrieve a list of devices", response = DeviceDTO.class),
			@ApiResponse(code = 400, message = "bad request. lack of mandatory fields or bad information in the request"),
			@ApiResponse(code = 500, message = "Internal Error") })
	@GetMapping()
	public List<DeviceDTO> getDevices(
			@RequestHeader(name = "status", defaultValue = "WAITING_FOR_ACTIVATION", required = false) SIMStatusType status,
			@RequestHeader(name = "configured", defaultValue = "false", required = false) Boolean configured,
			@RequestHeader(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestHeader(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestHeader(name = "orderValue", defaultValue = "serialNumber", required = false) String orderValue) throws InternalServerException {
		try {
			return deviceService.getDevicesByFilters(status, configured, pageNumber, pageSize, orderValue);

		} catch (Exception ex) {
			logger.error("Unexpected error on getDevices");
			throw new InternalServerException(ex.getMessage());
		}
	}

	@ApiOperation(value = "Create a device")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. device created", response = DeviceDTO.class),
			@ApiResponse(code = 400, message = "bad request. lack of mandatory fields or bad information in the request"),
			@ApiResponse(code = 409, message = "The device already exists"),
			@ApiResponse(code = 500, message = "Internal Error") })
	@PostMapping()
	public DeviceDTO createDevice(@RequestBody @Validated(DeviceDTO.DeviceDTOCreation.class) DeviceDTO device)
			throws BadRequestException, ConflictException, InternalServerException {
		try {
			return deviceService.saveDevice(device);
			
		}  catch (ConflictException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Unexpected error on createDevices");
			throw new InternalServerException(ex.getMessage());
		}

	}

	@ApiOperation(value = "update a device")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. device updated", response = DeviceDTO.class ),
            @ApiResponse(code = 400, message = "bad request. lack of mandatory fields or bad information in the request"),
            @ApiResponse(code = 409, message = "Conflict. Something went wrong"),
            @ApiResponse(code = 500, message = "Internal Error") })
    @PutMapping("/{id}")
    public DeviceDTO updateDevice(@PathVariable Long id, @RequestBody @Validated(DeviceDTO.DeviceDTOUpdate.class) DeviceDTO device) throws BadRequestException, ConflictException, InternalServerException {
        try {
            return deviceService.updateDevice(device);
            
        } catch(ConflictException ex) {
            throw ex;
        } catch(Exception ex) {
        	logger.error("Unexpected error on updateDevice");
            throw new InternalServerException(ex.getMessage());
        }
    }

	@ApiOperation(value = "Retrieve an specific device")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. device retrieved", response = DeviceDTO.class ),
            @ApiResponse(code = 400, message = "bad request. lack of mandatory fields or bad information in the request"),
            @ApiResponse(code = 500, message = "Internal Error") })
    @GetMapping("/{id}")
    public DeviceDTO retrieveSpecificDevice(@PathVariable Long id) throws InternalServerException {
        try {
            return deviceService.getDevice(id);
            
        } catch(Exception ex) {
        	logger.error("Unexpected error on retrieveSpecificDevice");
            throw new InternalServerException();
        }
    }

	@ApiOperation(value = "delete an specific device")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. device deleted", response = DeviceDTO.class),
			@ApiResponse(code = 400, message = "bad request. lack of mandatory fields or bad information in the request"),
			@ApiResponse(code = 500, message = "Internal Error") })
	@DeleteMapping("/{id}")
	public void deleteAnSpecificDevice(@PathVariable Long id) throws InternalServerException {
		try {
			deviceService.deleteDevice(id);
			
		} catch (Exception ex) {
			logger.error("Unexpected error on deleteAnSpecificDevice");
			throw new InternalServerException();
		}
	}
}
