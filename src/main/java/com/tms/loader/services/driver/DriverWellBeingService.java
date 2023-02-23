package com.tms.loader.services.driver;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tms.loader.entities.driver.Driver;
import com.tms.loader.entities.driver.DriverWellbeing;
import com.tms.loader.exceptions.ConstraintViolationExceptionHandler;
import com.tms.loader.exceptions.DataIntegrityExceptionHandler;
import com.tms.loader.exceptions.AllExceptionHandler;
import com.tms.loader.exceptions.ResourceNotFoundException;
import com.tms.loader.payloads.driver.DriverWellBeingDto;
import com.tms.loader.payloads.driver.DriverWellBeingResponseDto;
import com.tms.loader.repositories.driver.DriverRepo;
import com.tms.loader.repositories.driver.DriverWellBeingRepo;

@Service
public class DriverWellBeingService {
	@Autowired
	private DriverWellBeingRepo repo;
	@Autowired
	private DriverRepo driverRepo;
	@Autowired
	ModelMapper mapper;
	public DriverWellBeingDto addDriverHealthDetails(DriverWellBeingDto dto, Long id) {
		Driver driver = driverRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Driver", "id", id));
		DriverWellbeing driverWellBeing = mapper.map(dto, DriverWellbeing.class);
		driverWellBeing.setDriver(driver);
		try {
			repo.save(driverWellBeing);

		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityExceptionHandler();
		} catch (ConstraintViolationException e) {
			throw new ConstraintViolationExceptionHandler(dto.getHealthId().toString());
		}catch(Exception e) {
			throw new AllExceptionHandler();
		}
		return mapper.map(driverWellBeing, DriverWellBeingDto.class);
	}
	public List<DriverWellBeingResponseDto> getDriverHealthDetails() {
		List<Object[]> healthDetails = repo.findByDriverIdJoin();
		List<DriverWellBeingResponseDto> driverWellBeingdto = healthDetails.stream()
		                                        .map( dto ->{
		                                        	System.out.println("dto contains "+dto[0]);
		                                        	Driver driver = (Driver) dto[0]; 
		                                    		DriverWellbeing wellBeing = (DriverWellbeing) dto[1];      
		                                    		DriverWellBeingResponseDto resp = mapper.map(wellBeing, DriverWellBeingResponseDto.class);		                                    		
		                                    		mapper.map(driver, resp);
		                                    		return resp;
		                                        })
		                                        .collect(Collectors.toList());
		return driverWellBeingdto;
	}
//	 Performed join operation based on driverId
	public DriverWellBeingResponseDto getDriverHealthWithId(Long id){
		Object[] dto = null;
		try {
		dto = repo.findByDriverIdJoin(id);
			if (dto.length==0) {
					throw new ResourceNotFoundException("Health Status", "id", id);
			}
		}catch(Exception e) {
			throw new AllExceptionHandler(); 
		}
		Driver driver = (Driver) ((Object[]) dto[0])[0];
		DriverWellbeing wellBeing = (DriverWellbeing) ((Object[]) dto[0])[1];
		DriverWellBeingResponseDto resp = mapper.map(wellBeing, DriverWellBeingResponseDto.class);
		mapper.map(driver, resp);
		return resp;
	}
	public DriverWellBeingDto updateDriverHealthStatus(DriverWellBeingDto dto, Long id) {
		driverRepo.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("Driver Health Status", "Id", id));
		repo.setHealthInfoById(dto.getRestingHours(),dto.getBodyTemperature(),dto.isHasDisease(), dto.getDisease(), id);
		return mapper.map(repo.findByDriverId(id), DriverWellBeingDto.class);
	}
}
