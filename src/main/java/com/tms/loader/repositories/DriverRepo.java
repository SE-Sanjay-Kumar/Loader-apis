package com.tms.loader.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tms.loader.entities.driver.Driver;

public interface DriverRepo extends JpaRepository<Driver, Long> {

	@Query("SELECT d FROM Driver d JOIN DriverStatus ds on d.status.statusId = ds.statusId WHERE d.status.statusId = :id")
	List<Driver> findByStatusIdJoin(@Param("id") Long id);
}
