package com.tms.loader.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.loader.payloads.AdminDto;
import com.tms.loader.payloads.LoginDto;
import com.tms.loader.services.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@PostMapping("/")
	ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto dto){
		System.out.print("ready to create");
		AdminDto respdto = this.adminService.createAdmin(dto);
		return new ResponseEntity<AdminDto>(respdto, HttpStatus.CREATED);
	}
	@PostMapping("/login")
	ResponseEntity<?> authAdmin(@RequestBody LoginDto loginDto){
		boolean authenticated = adminService.authAdmin(loginDto.getUserName(), loginDto.getPassword());
		if(authenticated) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	@GetMapping("/")
	ResponseEntity<AdminDto> getAdmin(){
		return new ResponseEntity<AdminDto>(this.adminService.getAdmin(), HttpStatus.OK);
	}
}
