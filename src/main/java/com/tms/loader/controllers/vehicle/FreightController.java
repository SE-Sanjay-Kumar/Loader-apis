package com.tms.loader.controllers.vehicle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.loader.payloads.ApiResponse;
import com.tms.loader.payloads.vehicle.FreightDto;
import com.tms.loader.payloads.vehicle.UpdateFreightDto;
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
	@GetMapping("/{id}")
	public ResponseEntity<FreightDto> getFreight(@PathVariable Long id){
		return new ResponseEntity<FreightDto>(freightService.getFreight(id), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<FreightDto>> getAllFreights(){
		return new ResponseEntity<List<FreightDto>>(freightService.getAllFreights(), HttpStatus.OK);
	}
	@PutMapping("/{id}")
	public ResponseEntity<FreightDto> updateFreight(@RequestBody UpdateFreightDto dto, @PathVariable Long id){
		return new ResponseEntity<FreightDto>(freightService.updateFreight(dto,id), HttpStatus.OK);
		
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteFreight(@PathVariable Long id){
		boolean isDeleted = freightService.deleteFreight(id);
		return (isDeleted)? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	@GetMapping("/status/{statusId}")
	ResponseEntity<List<FreightDto>> getVehicleByStatusId(@PathVariable Long statusId){
		List<FreightDto> list = freightService.getVehiclesWithStatusId(statusId);
		return new ResponseEntity<List<FreightDto>>(list, HttpStatus.OK);
	}
	
}
