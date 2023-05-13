package com.tms.loader.controllers;

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


import com.tms.loader.payloads.ClientDto;
import com.tms.loader.payloads.LoginDto;
import com.tms.loader.payloads.LoginUserDetailDto;
import com.tms.loader.services.ClientService;

@RestController
@RequestMapping("/api/client")
public class ClientController {
	@Autowired
	private ClientService clientService;
	@PostMapping("/")
	ResponseEntity<ClientDto> createClient(@RequestBody ClientDto dto){
		ClientDto respdto = this.clientService.createClient(dto);
		System.out.println("This is new dto "+dto);
		return new ResponseEntity<ClientDto>(respdto, HttpStatus.CREATED);
	}
	@PostMapping("/login")
	ResponseEntity<?> authClient(@RequestBody LoginDto loginDto){
		LoginUserDetailDto userDetailDto = clientService.authClient(loginDto.getUserName(), loginDto.getPassword());
		if(userDetailDto != null) {
			return new ResponseEntity<LoginUserDetailDto>(userDetailDto, HttpStatus.OK);
		}
		return new ResponseEntity<LoginUserDetailDto>(userDetailDto, HttpStatus.UNAUTHORIZED);
	}
	@GetMapping("/")
	ResponseEntity<List<ClientDto>> getClients(){
		List<ClientDto> list = this.clientService.getClients();
		return new ResponseEntity<List<ClientDto>>(list, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	ResponseEntity<ClientDto> getClientById(@PathVariable Long id){
		ClientDto clientdto = clientService.getClientWithId(id);
		return new ResponseEntity<ClientDto>(clientdto, HttpStatus.OK);
		
	}
	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteClientById(@PathVariable Long id){
		boolean isDeleted = clientService.deleteClientWithId(id);
		return (isDeleted)? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);	
	}
	@PutMapping("/{id}")
	public ResponseEntity<ClientDto> updateClientWithId(@PathVariable Long id, @RequestBody ClientDto clientdto){
		return new ResponseEntity<ClientDto>(clientService.updateClient(clientdto, id), HttpStatus.OK);
	}
	
}
