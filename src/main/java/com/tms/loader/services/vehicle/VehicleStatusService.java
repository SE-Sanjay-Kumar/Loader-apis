package com.tms.loader.services.vehicle;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.tms.loader.entities.vehicle.VehicleStatus;
import com.tms.loader.exceptions.AllExceptionHandler;
import com.tms.loader.exceptions.ConstraintViolationExceptionHandler;
import com.tms.loader.exceptions.DataIntegrityExceptionHandler;
import com.tms.loader.exceptions.ResourceNotFoundException;
import com.tms.loader.payloads.StatusDto;
import com.tms.loader.repositories.vehicle.VehicleStatusRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class VehicleStatusService {
	  @PersistenceContext
	    private EntityManager em;
		@Autowired
		private VehicleStatusRepo repo;
		@Autowired
		private ModelMapper mapper;
		public StatusDto addStatus(StatusDto dto) {
			VehicleStatus statusEntity =  mapper.map(dto, VehicleStatus.class);
			Integer id = dto.getStatusId();
			if (repo.existsById(id)) {
				throw new DataIntegrityExceptionHandler();
			}
			try {
				repo.save(statusEntity);
			}catch (ConstraintViolationException e) {
				throw new ConstraintViolationExceptionHandler(dto.getStatus());
			}catch(DataIntegrityViolationException e) {
				throw new DataIntegrityExceptionHandler();
			}catch(Exception e) {
				System.out.println("Here");
				throw new AllExceptionHandler();
			}
			StatusDto respDto = mapper.map(statusEntity, StatusDto.class);
			return respDto;
		}
		public StatusDto getStatus(Integer id) {
			VehicleStatus statusEntity = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Status", "Id", id));
			return mapper.map(statusEntity, StatusDto.class);
		}
		public List<StatusDto> getAllStatus(){
			List<VehicleStatus> possibleStatusList = repo.findAll();
			List<StatusDto> vehicleStatusDtoList = possibleStatusList.stream()
	                .map(status -> mapper.map(status, StatusDto.class))
	                .collect(Collectors.toList());
			return vehicleStatusDtoList;
		}
		@Transactional
		public StatusDto updateStatus(StatusDto dto, Integer id) {
			repo.findById(id)
			.orElseThrow(()-> new ResourceNotFoundException("Vehicle Availability Status", "Id", id));
			try {
				System.out.println("status is "+dto.getStatus());
				repo.updateStatusById(dto.getStatus(), id);
			}catch(JpaSystemException e){
				throw new ConstraintViolationExceptionHandler(dto.getStatus());
			}catch(Exception e) {
				throw new AllExceptionHandler();
			}
			
			VehicleStatus status = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Driver Availability Status", "Id", id));;
			em.refresh(status);
			System.out.println(status.getStatus()+" :status ");
			return mapper.map(status, StatusDto.class);
		}
}
