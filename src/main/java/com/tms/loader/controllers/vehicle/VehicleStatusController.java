package com.tms.loader.controllers.vehicle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tms.loader.payloads.StatusDto;
import com.tms.loader.services.vehicle.VehicleStatusService;

@Controller
@RequestMapping("/api/vehicle-statuses")
public class VehicleStatusController {
	@Autowired
	private VehicleStatusService service;
	@PostMapping("/")
	public ResponseEntity<StatusDto> addStatus(@RequestBody StatusDto dto) {
		return new ResponseEntity<StatusDto>(service.addStatus(dto), HttpStatus.CREATED);
	}
	@GetMapping("/{id}")
	public ResponseEntity<StatusDto> getStatus(@PathVariable Integer id) {
		return new ResponseEntity<StatusDto>(service.getStatus(id), HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<List<StatusDto>> getAllStatus() {
		return new ResponseEntity<List<StatusDto>>(service.getAllStatus(), HttpStatus.OK);
	}
	@PutMapping("/{id}")
	public ResponseEntity<StatusDto> updateStatus(@RequestBody StatusDto dto, @PathVariable Integer id) {
		return new ResponseEntity<StatusDto>(service.updateStatus(dto, id), HttpStatus.OK);
	}
}
