package com.tms.loader.services.vehicle;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tms.loader.entities.vehicle.VehicleType;
import com.tms.loader.exceptions.ConstraintViolationExceptionHandler;
import com.tms.loader.exceptions.DataIntegrityExceptionHandler;
import com.tms.loader.exceptions.ResourceNotFoundException;
import com.tms.loader.exceptions.AllExceptionHandler;
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
		}catch(DataIntegrityViolationException e) {
			
			throw new DataIntegrityExceptionHandler();
		} catch (ConstraintViolationException e) {
			throw new ConstraintViolationExceptionHandler(dto.getTypeName());
		}catch(Exception e) {
			throw new AllExceptionHandler();
		}
        return mapper.map(vtype, VehicleTypeRespDto.class);
	}
	public VehicleTypeRespDto getVehicleType(Integer id) {

		VehicleType vehicleType = repo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("vehicle type", "Id", id));
		return mapper.map(vehicleType, VehicleTypeRespDto.class);
	}
	public List<VehicleTypeRespDto> getAllVehicleTypes() {

		List<VehicleType> vehicleTypes = repo.findAll();
		List<VehicleTypeRespDto> vehicleTypeDtos = vehicleTypes.stream()
		                                        .map(type -> mapper.map(type, VehicleTypeRespDto.class))
		                                        .collect(Collectors.toList());
		return vehicleTypeDtos;

	}
	public boolean deleteVehicleType(Integer id) {
		VehicleTypeRespDto dto = this.getVehicleType(id);
		repo.deleteById(dto.getTypeId());
		return true;
	
	}
}
