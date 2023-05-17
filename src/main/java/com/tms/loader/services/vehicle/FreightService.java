package com.tms.loader.services.vehicle;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tms.loader.configs.ModelMapperConfig;
import com.tms.loader.entities.vehicle.Freight;
import com.tms.loader.entities.vehicle.VehicleCost;
import com.tms.loader.entities.vehicle.VehicleType;
import com.tms.loader.exceptions.ConstraintViolationExceptionHandler;
import com.tms.loader.exceptions.DataIntegrityExceptionHandler;
import com.tms.loader.exceptions.ResourceNotFoundException;
import com.tms.loader.payloads.vehicle.FreightDto;
import com.tms.loader.payloads.vehicle.UpdateFreightDto;
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
		if (dto.getType() == null ) {
			throw new ConstraintViolationExceptionHandler(dto.getName());
		}
		Freight freightEntity =  mapper.map(dto, Freight.class);
		VehicleType vehicleType = vtypeRepo.findById(dto.getType().getTypeId()).orElseThrow(()-> new ResourceNotFoundException("VehicleType", "id", dto.getType().getTypeId()));
		freightEntity.setVtype(vehicleType);
		
		try {
			repo.save(freightEntity);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityExceptionHandler();
		} catch (ConstraintViolationException e) {
			throw new ConstraintViolationExceptionHandler(dto.getName());
		}
		FreightDto fdto = mapper.map(freightEntity, FreightDto.class);
		return fdto;
	}
	public FreightDto getFreight(Long id) {

		ModelMapperConfig.mapBasedOnFreightId(mapper);
		Freight freight = repo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Freight", "Id", id));
		return mapper.map(freight, FreightDto.class);
	}
	public List<FreightDto> getAllFreights() {

		ModelMapperConfig.mapBasedOnFreightId(mapper);
		List<Freight> freights = repo.findAll();
		List<FreightDto> freightDtoList = freights.stream()
		                                        .map(freight -> mapper.map(freight, FreightDto.class))
		                                        .collect(Collectors.toList());
		return freightDtoList;

	}
	public boolean deleteFreight(Long id) {
		FreightDto dto = this.getFreight(id);
		repo.deleteById(dto.getVehicleId());
		return true;
	}
	public FreightDto updateFreight(UpdateFreightDto orderDto, Long id) {
		VehicleCost cost = new VehicleCost();
		cost.setMaintenanceCost(orderDto.getMaintenanceCost());
		cost.setFuelCost(orderDto.getFuelCost());
		repo.updateFreightById(orderDto.getStatus(),cost, id);
		Freight updatedFreight = repo.findById(id).orElseThrow(()->  new ResourceNotFoundException("Freight","id", id));
		return mapper.map(updatedFreight, FreightDto.class);
		
	}
	public List<FreightDto> getVehiclesWithStatusId(Long id) {
		
		List<Freight> freightStatusList = repo.findByStatusIdJoin(id);
		if (freightStatusList.size()==0) {
			throw new ResourceNotFoundException("Status", "Id",id);
		}

		List<FreightDto> freightDtoList= freightStatusList.stream()
			                                        .map(freight -> mapper.map(freight, FreightDto.class))
			                                        .collect(Collectors.toList());
		
		
		return freightDtoList;
	}
}
