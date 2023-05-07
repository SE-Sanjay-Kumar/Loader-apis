package com.tms.loader.repositories.driver;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tms.loader.entities.driver.Driver;
import com.tms.loader.entities.driver.DriverStatus;

import jakarta.transaction.Transactional;

public interface DriverRepo extends JpaRepository<Driver, Long> {

	@Query("SELECT d FROM Driver d JOIN DriverStatus ds on d.status.statusId = ds.statusId WHERE d.status.statusId = :id")
	List<Driver> findByStatusIdJoin(@Param("id") Long id);
	
	@Query("SELECT d,v FROM Driver d JOIN Freight v on d.vehicle = v.vehicleId")
	public List<Object[]> findByVehicleIdJoin();
	
	@Transactional
	@Modifying
	@Query("update Driver set status= ?1, userName=?2 where id=?3")
	int updateDriverById(DriverStatus status,String userName, Long id);

	
}
