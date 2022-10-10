package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.api.response.DeviceDTO;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.model.Device;
import com.example.demo.model.SIMStatusType;
import com.example.demo.repository.IDeviceRepository;


@Service
public class DeviceService implements IDeviceService {

	@Autowired
	private IDeviceRepository deviceRepository;

	@Autowired
	private ISimService simService;

	@Autowired
	private DozerBeanMapper mapper;

	@Autowired
	private Logger logger;

	@Value("${temperature.min}")
	private Double minTemperature;

	@Value("${temperature.max}")
	private Double maxTemperature;

	@Override
	public DeviceDTO saveDevice(DeviceDTO deviceDTO) throws InternalServerException, ConflictException {
		try {
			if (deviceDTO.getSim() != null) {
				deviceDTO.setSim(simService.save(deviceDTO.getSim()));
			}
			Device device = mapper.map(deviceDTO, Device.class);
			deviceRepository.save(device);
			return mapper.map(device, DeviceDTO.class);
			
		} catch (ConstraintViolationException | DataIntegrityViolationException ex) {
			throw new ConflictException(ex.getMessage());
		} catch (Exception ex) {
			throw new InternalServerException(ex.getMessage());
		}
	}

	@Override
	public DeviceDTO updateDevice(DeviceDTO deviceDTO) throws ConflictException, InternalServerException {
		try {
			Optional<Device> originalDeviceOptional = deviceRepository.findById(deviceDTO.getId());
			if (originalDeviceOptional.isPresent()) {
				Device device = originalDeviceOptional.get();
				device = mapper.map(deviceDTO, Device.class);
				device = deviceRepository.save(device);
				return mapper.map(device, DeviceDTO.class);
			} else {
				throw new ConflictException("the device that are you trying to update is not created");
			}

		} catch (ConstraintViolationException ex) {
			throw new ConflictException(ex.getMessage());
		} catch (Exception ex) {
			throw new InternalServerException(ex.getMessage());
		}
	}

	@Override
	public DeviceDTO getDevice(Long id) throws BadRequestException, InternalServerException {
		try {
		Optional<Device> originalDeviceOptional = deviceRepository.findById(id);
		if (!originalDeviceOptional.isPresent()) {
			throw new BadRequestException("The device doesn't exists");
		} 
		return mapper.map(originalDeviceOptional.get(), DeviceDTO.class);
		} catch (Exception ex) {
			logger.error("Exception on method getDevice");
			throw new InternalServerException(ex.getMessage());
		}
	}

	@Override
	public void deleteDevice(Long id) throws InternalServerException {
		try {
			deviceRepository.deleteById(id);
			
		} catch(Exception ex) {
			logger.error("Exception on method deleteDevice");
			throw new InternalServerException(ex.getMessage());
		}
	}

	@Override
	public List<DeviceDTO> getDevicesByFilters(SIMStatusType status, Boolean configured, Integer pageNumber,
			Integer pageSize, String orderValue) throws InternalServerException {
		try {
			Pageable pageable = PageRequest.of(pageNumber.intValue(), pageSize.intValue(), Sort.by(orderValue).descending());
			List<Device> result = new ArrayList<>();
			if (configured) {
				result = deviceRepository.getAllDevicesConfigured(minTemperature, maxTemperature, pageable);

			} else {
				result = deviceRepository.getAllDevicesByStatus(status.toString(), pageable);
			}

			logger.info("result amount {}", result.size());
			return result.stream().map(x -> mapper.map(x, DeviceDTO.class)).toList();
		} catch (InvalidDataAccessApiUsageException e) {
			logger.warn("Not Found devices on the DB with the status "+ status.toString());
		} catch (Exception ex) {
			logger.error("Exception on method getDevicesByFilters");
			throw new InternalServerException(ex.getMessage());
		}
		return null; 
	}
}
