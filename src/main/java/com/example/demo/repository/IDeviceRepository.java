package com.example.demo.repository;

import com.example.demo.model.Device;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDeviceRepository extends JpaRepository<Device, Long> {

	
	
	  @Query(value =
	  "SELECT * FROM devices d INNER JOIN d.sims s on s.id=d.sim_id WHERE s.status = :status", nativeQuery = true)
	  List<Device> getAllDevicesByStatus(@Param("status") String status, Pageable
	  pageable);
	 
	  
	
	  @Query(value =
	  "SELECT * FROM devices d INNER JOIN d.sims s on s.id=d.sim_id WHERE s.status = 'ACTIVE' AND d.temperature > :min AND d.temperature < :max", nativeQuery = true
	  ) List<Device> getAllDevicesConfigured(@Param("min") Double
	  minTemperature, @Param("max") Double maxTemperature, Pageable pageable);
	 
	
}
