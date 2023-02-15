package com.tms.loader.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.tms.loader.entities.driver.DriverStatus;
import com.tms.loader.exceptions.CJpaSystemException;
import com.tms.loader.exceptions.ExceptionEnd;
import com.tms.loader.exceptions.ResourceNotFoundException;
import com.tms.loader.payloads.StatusDto;
import com.tms.loader.repositories.DriverStatusRepo;

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
		try {
			repo.save(statusEntity);
		}catch (JpaSystemException e) {
			throw new CJpaSystemException(dto.getStatus());
		}catch(Exception e) {
			throw new ExceptionEnd();
		}
		System.out.println(statusEntity.getStatus());
		StatusDto respDto = mapper.map(statusEntity, StatusDto.class);
		System.out.println(respDto.getStatus());
		return respDto;
	}
	public StatusDto getStatus(Long id) {
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
	public StatusDto updateStatus(StatusDto dto, Long id) {
		repo.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("Driver Availability Status", "Id", id));
		try {
			System.out.println("status is "+dto.getStatus());
			repo.updateStatusById(dto.getStatus(), id);
		}catch(JpaSystemException e){
			throw new CJpaSystemException(dto.getStatus());
		}catch(Exception e) {
			throw new ExceptionEnd();
		}
		
		DriverStatus status = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Driver Availability Status", "Id", id));;
		em.refresh(status);
		System.out.println(status.getStatus()+" :status ");
		return mapper.map(status, StatusDto.class);
	}
}
