package com.tms.loader.controllers.vehicle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.loader.payloads.ApiResponse;
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
	@GetMapping("/{id}")
	public ResponseEntity<VehicleTypeRespDto> getVehicleType(@PathVariable Integer id){
		return new ResponseEntity<VehicleTypeRespDto>(service.getVehicleType(id), HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<List<VehicleTypeRespDto>> getVehiceTypes(){
		return new ResponseEntity<List<VehicleTypeRespDto>>(service.getAllVehicleTypes(), HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteVehicleType(@PathVariable Integer id){
		boolean isDeleted = service.deleteVehicleType(id);
		return (isDeleted)? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
