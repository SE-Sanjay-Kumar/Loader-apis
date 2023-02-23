package com.tms.loader.repositories.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tms.loader.entities.vehicle.VehicleType;

public interface VehicleTypeRepo extends JpaRepository<VehicleType, Integer> {

}
