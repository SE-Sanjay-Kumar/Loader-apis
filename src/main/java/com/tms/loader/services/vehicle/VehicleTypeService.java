package com.tms.loader.services.vehicle;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tms.loader.entities.vehicle.VehicleType;
import com.tms.loader.exceptions.ConstraintViolationExceptionHandler;
import com.tms.loader.exceptions.ExceptionEnd;
import com.tms.loader.payloads.vehicle.VehicleTypeDto;
import com.tms.loader.payloads.vehicle.VehicleTypeRespDto;
import com.tms.loader.repositories.vehicle.VehicleTypeRepo;

@Service
public class VehicleTypeService {
	@Autowired
	private VehicleTypeRepo repo;
	@Autowired
	ModelMapper mapper;
	public VehicleTypeRespDto addNewTypeOfVehicle(VehicleTypeDto dto) {
		VehicleType vtype =  mapper.map(dto, VehicleType.class);
		try {
			repo.save(vtype);
		}catch (ConstraintViolationException e) {
			throw new ConstraintViolationExceptionHandler(dto.getTypeName());
		}catch(Exception e) {
			throw new ExceptionEnd();
		}
        return mapper.map(vtype, VehicleTypeRespDto.class);
	}
}
