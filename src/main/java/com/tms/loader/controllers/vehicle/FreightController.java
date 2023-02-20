package com.tms.loader.controllers.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.loader.payloads.vehicle.FreightDto;
import com.tms.loader.services.vehicle.FreightService;

@RestController
@RequestMapping("/api/vehicle")
public class FreightController {
	@Autowired
	private FreightService freightService;
	@PostMapping("/")
	public ResponseEntity<FreightDto> addFreight(@RequestBody FreightDto dto) {
		return new ResponseEntity<FreightDto>(freightService.addFreight(dto), HttpStatus.CREATED);
	}
}
