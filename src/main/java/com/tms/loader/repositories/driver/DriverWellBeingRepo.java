package com.tms.loader.repositories.driver;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tms.loader.entities.driver.DriverWellbeing;

import jakarta.transaction.Transactional;

public interface DriverWellBeingRepo extends JpaRepository<DriverWellbeing, Long> {
	@Query("SELECT d,dw FROM Driver d JOIN DriverWellbeing dw on d.id = dw.driver WHERE d.id = :driverId")
    public Object[] findByDriverIdJoin(@Param("driverId") Long driverId);
	@Query("SELECT d,dw FROM Driver d JOIN DriverWellbeing dw on d.id = dw.driver")
	public List<Object[]> findByDriverIdJoin();
	@Transactional
	@Modifying
	@Query("update DriverWellbeing wb set wb.restingHours = ?1, wb.bodyTemperature = ?2, wb.hasDisease = ?3, wb.disease = ?4 where wb.driver.id = ?5")
	int setHealthInfoById(int restingHours, float bodyTemperature, boolean hasDisease, String disease,Long id);
	DriverWellbeing findByDriverId(Long Id);
}
