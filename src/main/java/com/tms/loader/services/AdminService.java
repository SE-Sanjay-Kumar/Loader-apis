package com.tms.loader.services;


import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.tms.loader.exceptions.ConstraintViolationExceptionHandler;
import com.tms.loader.exceptions.ExceptionEnd;

import org.springframework.stereotype.Service;

import com.tms.loader.entities.Admin;
import com.tms.loader.payloads.AdminDto;
import com.tms.loader.repositories.AdminRepo;

@Service
public class AdminService {
	@Autowired
	private AdminRepo adminRepo;
	@Autowired
	private ModelMapper mapper;
	public AdminDto createAdmin(AdminDto adminDto){
		Admin admin = mapper.map(adminDto, Admin.class);
		try {
			adminRepo.save(admin);
		}catch (ConstraintViolationException e) {
			throw new ConstraintViolationExceptionHandler(adminDto.getUserName());
		}catch(Exception e) {
			throw new ExceptionEnd();
		}
		return mapper.map(admin, AdminDto.class);
	}
	public AdminDto getAdmin() {
		return mapper.map(adminRepo.getAdmin(), AdminDto.class);
	}
	
	
}
