package com.tms.loader.repositories.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tms.loader.entities.vehicle.Freight;

public interface FreightRepo extends JpaRepository<Freight, Long> {

}
