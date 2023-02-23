package com.tms.loader.services.driver;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.tms.loader.entities.driver.DriverStatus;
import com.tms.loader.exceptions.ConstraintViolationExceptionHandler;
import com.tms.loader.exceptions.DataIntegrityExceptionHandler;
import com.tms.loader.exceptions.AllExceptionHandler;
import com.tms.loader.exceptions.ResourceNotFoundException;
import com.tms.loader.payloads.StatusDto;
import com.tms.loader.repositories.driver.DriverStatusRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class DriverStatusService {

    @PersistenceContext
    private EntityManager em;
	@Autowired
	private DriverStatusRepo repo;
	@Autowired
	private ModelMapper mapper;
	public StatusDto addStatus(StatusDto dto) {
		DriverStatus statusEntity =  mapper.map(dto, DriverStatus.class);
		Integer id = dto.getStatusId();
		if (repo.existsById(id)) {
			throw new DataIntegrityExceptionHandler();
		}
		try {
			repo.save(statusEntity);
			System.out.println("Saved");
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
		DriverStatus statusEntity = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Status", "Id", id));
		return mapper.map(statusEntity, StatusDto.class);
	}
	public List<StatusDto> getAllStatus(){
		List<DriverStatus> possibleStatusList = repo.findAll();
		List<StatusDto> driverStatusDtoList = possibleStatusList.stream()
                .map(status -> mapper.map(status, StatusDto.class))
                .collect(Collectors.toList());
		return driverStatusDtoList;
	}
	@Transactional
	public StatusDto updateStatus(StatusDto dto, Integer id) {
		repo.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("Driver Availability Status", "Id", id));
		try {
			System.out.println("status is "+dto.getStatus());
			repo.updateStatusById(dto.getStatus(), id);
		}catch(JpaSystemException e){
			throw new ConstraintViolationExceptionHandler(dto.getStatus());
		}catch(Exception e) {
			throw new AllExceptionHandler();
		}
		
		DriverStatus status = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Driver Availability Status", "Id", id));;
		em.refresh(status);
		System.out.println(status.getStatus()+" :status ");
		return mapper.map(status, StatusDto.class);
	}
}
