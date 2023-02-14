package com.tms.loader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tms.loader.entities.driver.Driver;

public interface DriverRepo extends JpaRepository<Driver, Long> {

}
