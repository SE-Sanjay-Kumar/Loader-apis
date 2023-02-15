package com.tms.loader.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.loader.payloads.driver.DriverDto;
import com.tms.loader.services.DriverService;

@RestController
@RequestMapping("/api/driver")
public class DriverController {
	@Autowired
	private DriverService service;
	@PostMapping("/")
	ResponseEntity<DriverDto> createDriver(@RequestBody DriverDto dto){
		DriverDto respdto = this.service.addDriver(dto);
		System.out.println("This is new dto "+respdto);
		return new ResponseEntity<DriverDto>(respdto, HttpStatus.CREATED);
	}
	@GetMapping("/")
	ResponseEntity<List<DriverDto>> getDrivers(){
		List<DriverDto> list = this.service.getDrivers();
		return new ResponseEntity<List<DriverDto>>(list, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	ResponseEntity<DriverDto> getDriverById(@PathVariable Long id){
		DriverDto driverDto = this.service.getClientWithId(id);
		return new ResponseEntity<DriverDto>(driverDto, HttpStatus.OK);
	}
	@GetMapping("/status/{statusId}")
	ResponseEntity<List<DriverDto>> getDriverByStatusId(@PathVariable Long statusId){
		System.out.println("Hello inside status id join");
		List<DriverDto> list = this.service.getDriversWithStatusId(statusId);
		return new ResponseEntity<List<DriverDto>>(list, HttpStatus.OK);
	}
}
