package com.tms.loader.repositories.vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tms.loader.entities.vehicle.Freight;

public interface FreightRepo extends JpaRepository<Freight, Long> {
	@Query("SELECT d FROM Freight d JOIN VehicleStatus ds on d.status.statusId = ds.statusId WHERE d.status.statusId = :id")
	List<Freight> findByStatusIdJoin(@Param("id") Long id);
}
