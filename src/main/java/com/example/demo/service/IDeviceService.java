package com.example.demo.service;

import java.util.List;

import com.example.demo.api.response.DeviceDTO;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.model.SIMStatusType;

public interface IDeviceService {

    DeviceDTO saveDevice(DeviceDTO device) throws InternalServerException, ConflictException;

    DeviceDTO updateDevice(DeviceDTO device) throws ConflictException, InternalServerException;

    DeviceDTO getDevice(Long id) throws BadRequestException, InternalServerException;

    void deleteDevice(Long id) throws InternalServerException;

	List<DeviceDTO> getDevicesByFilters(SIMStatusType status, Boolean configured, Integer pageNumber, Integer pageSize,
			String orderValue) throws InternalServerException;
}
