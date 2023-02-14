package com.tms.loader.services;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import com.tms.loader.exceptions.CJpaSystemException;
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
		}catch(JpaSystemException jpaexception) {
			throw new CJpaSystemException(adminDto.getUserName());
		}catch(Exception e) {
			throw new ExceptionEnd();
		}
		return mapper.map(admin, AdminDto.class);
	}
	public AdminDto getAdmin() {
		return mapper.map(adminRepo.getAdmin(), AdminDto.class);
	}
	
	
}
