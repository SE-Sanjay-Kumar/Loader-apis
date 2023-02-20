package com.tms.loader.services.vehicle;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tms.loader.configs.ModelMapperConfig;
import com.tms.loader.entities.vehicle.Freight;
import com.tms.loader.entities.vehicle.VehicleType;
import com.tms.loader.exceptions.ConstraintViolationExceptionHandler;
import com.tms.loader.exceptions.ExceptionEnd;
import com.tms.loader.exceptions.ResourceNotFoundException;
import com.tms.loader.payloads.vehicle.FreightDto;
import com.tms.loader.repositories.vehicle.FreightRepo;
import com.tms.loader.repositories.vehicle.VehicleTypeRepo;

@Service
public class FreightService {
	@Autowired
	private FreightRepo repo;
	@Autowired
	private VehicleTypeRepo vtypeRepo;
	@Autowired
	private ModelMapper mapper;
	
	public FreightDto addFreight(FreightDto dto) {
		ModelMapperConfig.mapBasedOnFreightId(mapper);
		Long typeId =  dto.getTypeId();
		VehicleType vehicleType = vtypeRepo.findById(typeId).orElseThrow(()-> new ResourceNotFoundException("VehicleType", "id", typeId));
		Freight freightEntity =  mapper.map(dto, Freight.class);
		freightEntity.setVtype(vehicleType);
		try {
			repo.save(freightEntity);
		}catch (ConstraintViolationException e) {
			throw new ConstraintViolationExceptionHandler(dto.getName());
		}catch(Exception e) {
			throw new ExceptionEnd();
		}
		System.out.println(freightEntity.getVtype().getTypeId());
		FreightDto fdto = mapper.map(freightEntity, FreightDto.class);
		System.out.println(fdto.getTypeId());
		return fdto;
	}
}
