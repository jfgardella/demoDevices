package com.example.demo.service;

import com.example.demo.api.response.SIMDTO;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.InternalServerException;

public interface ISimService {

	SIMDTO save(SIMDTO sim) throws ConflictException, InternalServerException;

	SIMDTO updateSIM(SIMDTO simDTO) throws ConflictException, InternalServerException;

	SIMDTO getSIM(Long id) throws BadRequestException, InternalServerException;

	void deleteSIM(Long id) throws InternalServerException;


}
