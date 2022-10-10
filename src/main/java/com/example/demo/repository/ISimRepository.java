package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.SIM;

@Repository
public interface ISimRepository extends JpaRepository<SIM, Long> {

	Optional<SIM> findBySimId(String simId);

}
