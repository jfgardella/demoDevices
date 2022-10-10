package com.example.demo.service;

import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.example.demo.api.response.SIMDTO;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.model.SIM;
import com.example.demo.repository.ISimRepository;

@Service
public class SimService implements ISimService {

	@Autowired
	private ISimRepository simRepository;

	@Autowired
	private Logger logger;

	@Autowired
	private DozerBeanMapper mapper;

	@Override
	public SIMDTO save(SIMDTO simDTO) throws ConflictException, InternalServerException {
		try {
			SIM sim = mapper.map(simDTO, SIM.class);
			Optional<SIM> checkIfExists = simRepository.findBySimId(simDTO.getSimId());
			if (checkIfExists.isPresent()) {
				throw new ConflictException("Already exists a SIM with the same SIM ID");
			}
			SIM result = simRepository.save(sim);
			return mapper.map(result, SIMDTO.class);

		} catch (ConflictException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception on method saveSIM");
			throw new InternalServerException(ex.getMessage());
		}
	}

	@Override
	public SIMDTO updateSIM(SIMDTO simDTO) throws ConflictException, InternalServerException {
		try {
			Optional<SIM> simOptional = simRepository.findById(simDTO.getId());

			SIM sim = simOptional.get();
			sim = mapper.map(simDTO, SIM.class);
			sim = simRepository.save(sim);
			return mapper.map(sim, SIMDTO.class);

		} catch (InvalidDataAccessApiUsageException ex) {
			throw new ConflictException("the sim with the id: " + simDTO.getId() + " doesn't exits on the DB");
		} catch (Exception ex) {
			logger.error("Exception on method updateSIM");
			throw new InternalServerException(ex.getMessage());
		}
	}

	@Override
	public SIMDTO getSIM(Long id) throws BadRequestException, InternalServerException {
		try {
			Optional<SIM> simOptional = simRepository.findById(id);
			if (!simOptional.isPresent()) {
				throw new BadRequestException("The sim doesn't exists");
			}
			return mapper.map(simOptional.get(), SIMDTO.class);

		} catch (Exception ex) {
			logger.error("Exception on method getSIM");
			throw new InternalServerException(ex.getMessage());
		}
	}

	@Override
	public void deleteSIM(Long id) throws InternalServerException {
		try {
			simRepository.deleteById(id);

		} catch (Exception ex) {
			logger.error("Exception on method deleteSIM");
			throw new InternalServerException(ex.getMessage());
		}

	}

}
