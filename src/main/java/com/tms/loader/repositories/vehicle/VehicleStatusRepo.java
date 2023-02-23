package com.tms.loader.repositories.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tms.loader.entities.vehicle.VehicleStatus;

import jakarta.transaction.Transactional;

public interface VehicleStatusRepo extends JpaRepository<VehicleStatus, Integer> {
	@Transactional
	@Modifying
	@Query("update VehicleStatus vs set vs.status = ?1 where vs.statusId = ?2")
	int updateStatusById(String status,Integer id);
}
