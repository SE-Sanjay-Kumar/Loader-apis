package com.tms.loader.controllers.driver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.loader.payloads.driver.DriverWellBeingDto;
import com.tms.loader.payloads.driver.DriverWellBeingResponseDto;
import com.tms.loader.services.driver.DriverWellBeingService;

@RestController
@RequestMapping("/api/driver")
public class DriverWellBeingController {
	@Autowired
	private DriverWellBeingService service;
	@PostMapping("/{id}/health/")
	ResponseEntity<DriverWellBeingDto> addHealthDetails(@PathVariable Long id, @RequestBody DriverWellBeingDto dto){
		DriverWellBeingDto respdto = this.service.addDriverHealthDetails(dto, id);
		return new ResponseEntity<DriverWellBeingDto>(respdto, HttpStatus.CREATED);
	}
	@GetMapping("/{id}/health/")
	ResponseEntity<DriverWellBeingResponseDto> getHeatlhDetails(@PathVariable Long id){
		DriverWellBeingResponseDto respdto = this.service.getDriverHealthWithId(id);
		return new ResponseEntity<DriverWellBeingResponseDto>(respdto, HttpStatus.OK);
	}
	@GetMapping("/health/")
	ResponseEntity<List<DriverWellBeingResponseDto>> getAllHealthDetails(){
		
		List<DriverWellBeingResponseDto> dto = this.service.getDriverHealthDetails();
		return new ResponseEntity<List<DriverWellBeingResponseDto>>(dto,HttpStatus.OK);
	}
	@PutMapping("/{id}/health/")
	ResponseEntity<DriverWellBeingDto> updateHealthDetails(@PathVariable Long id, @RequestBody DriverWellBeingDto dto){
		DriverWellBeingDto driverWellBeingDto =  this.service.updateDriverHealthStatus(dto, id);
		return new ResponseEntity<DriverWellBeingDto>(driverWellBeingDto,HttpStatus.OK);
	}
	
}
