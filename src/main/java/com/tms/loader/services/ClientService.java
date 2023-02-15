package com.tms.loader.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.tms.loader.entities.Client;
import com.tms.loader.exceptions.CJpaSystemException;
import com.tms.loader.exceptions.ExceptionEnd;
import com.tms.loader.exceptions.ResourceNotFoundException;
import com.tms.loader.payloads.ClientDto;
import com.tms.loader.repositories.ClientRepo;

@Service
public class ClientService {
	@Autowired
	private ClientRepo clientRepo;
	@Autowired
	private ModelMapper mapper;
	public ClientDto createClient(ClientDto dto) {
		Client clientEntity = this.mapper.map(dto, Client.class);
		try {
			clientRepo.save(clientEntity);
		}catch (JpaSystemException e) {
			throw new CJpaSystemException(dto.getUserName());
		}catch(Exception e) {
			throw new ExceptionEnd();
		}
		return this.mapper.map(clientEntity, ClientDto.class);
	}
	
	public List<ClientDto> getClients(){
		List<Client> clients = clientRepo.findAll();
		List<ClientDto> clientsDtoList = clients.stream()
		                                        .map(client -> mapper.map(client, ClientDto.class))
		                                        .collect(Collectors.toList());
		return clientsDtoList;
	}
	public ClientDto getClientWithId(Long id){
		Client client = clientRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Client", "Id", id));
		return mapper.map(client, ClientDto.class);
	}
	public boolean deleteClientWithId(Long id) {
		ClientDto dto = this.getClientWithId(id);
		clientRepo.deleteById(dto.getId());
		return true;
		
	}
	public ClientDto updateClient(ClientDto dto, Long id) {
		getClientWithId(id);
		clientRepo.setClientInfoById(dto.getUserName(),dto.getPassword() , dto.getCnic(), dto.getPhoneNumber(), dto.getCompanyName(), id);
		return mapper.map(clientRepo.findById(id), ClientDto.class);
		
	}
}
