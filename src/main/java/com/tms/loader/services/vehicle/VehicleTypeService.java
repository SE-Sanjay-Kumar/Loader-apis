package com.tms.loader.services.vehicle;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tms.loader.entities.vehicle.VehicleType;
import com.tms.loader.payloads.vehicle.VehicleTypeDto;
import com.tms.loader.payloads.vehicle.VehicleTypeRespDto;
import com.tms.loader.repositories.vehicle.VehicleTypeRepo;
import com.tms.loader.utils.CRUDClass;

@Service
public class VehicleTypeService {
	@Autowired
	private VehicleTypeRepo repo;
	@Autowired
	CRUDClass crud;
	@Autowired
	ModelMapper mapper;
	public VehicleTypeRespDto addNewTypeOfVehicle(VehicleTypeDto dto) {
		VehicleType type = crud.saveDTOToRepository(dto, repo, VehicleType.class);
        return mapper.map(type, VehicleTypeRespDto.class);
	}
}
