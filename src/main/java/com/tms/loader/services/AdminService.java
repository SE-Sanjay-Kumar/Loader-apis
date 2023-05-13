package com.tms.loader.services;


import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;

import com.tms.loader.exceptions.AllExceptionHandler;
import com.tms.loader.exceptions.ConstraintViolationExceptionHandler;
import com.tms.loader.exceptions.DataIntegrityExceptionHandler;
import com.tms.loader.exceptions.JPAExceptionHandler;

import org.springframework.stereotype.Service;

import com.tms.loader.entities.Admin;
import com.tms.loader.payloads.AdminDto;
import com.tms.loader.payloads.LoginUserDetailDto;
import com.tms.loader.repositories.AdminRepo;

@Service
public class AdminService {
	@Autowired
	private AdminRepo adminRepo;
	@Autowired
	private ModelMapper mapper;
	public AdminDto createAdmin(AdminDto adminDto) {
		Admin admin = mapper.map(adminDto, Admin.class);
		try {
			adminRepo.save(admin);
		}catch (ConstraintViolationException e) {
			throw new ConstraintViolationExceptionHandler(adminDto.getUserName());
		}catch(DataIntegrityExceptionHandler e) {
			throw new DataIntegrityExceptionHandler();
		}catch(JpaSystemException e) {
			throw new JPAExceptionHandler();
		}catch(Exception e) {
			throw new AllExceptionHandler();
		}
		return mapper.map(admin, AdminDto.class);
	}
	
	public AdminDto getAdmin() {
		System.out.println("getter admin");
		return mapper.map(adminRepo.getAdmin(), AdminDto.class);
	}
	public LoginUserDetailDto authAdmin(String username, String password) {
		Admin admin = adminRepo.findByuserName(username);
	    if (admin == null) {
	        // If no admin user with the given username exists, return false
	        return null;
	    } else {
	        // If an admin user with the given username exists, check if the password matches
	        if(admin.getPassword().equals(password)) {
	        	return mapper.map(admin, LoginUserDetailDto.class);
	        }return null;
	    }
	}
	
}
