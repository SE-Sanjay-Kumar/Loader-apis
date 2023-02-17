package com.tms.loader.controllers.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.loader.payloads.vehicle.VehicleTypeDto;
import com.tms.loader.payloads.vehicle.VehicleTypeRespDto;
import com.tms.loader.services.vehicle.VehicleTypeService;

@RestController
@RequestMapping("/api/vehicle-type")
public class VehicleTypeController {
	@Autowired
	private VehicleTypeService service;
	@PostMapping("/")
	public ResponseEntity<VehicleTypeRespDto> addNewVehicleType(@RequestBody VehicleTypeDto dto) {
		VehicleTypeRespDto resp =  service.addNewTypeOfVehicle(dto);
		return new ResponseEntity<VehicleTypeRespDto>(resp, HttpStatus.CREATED);
	}
}
