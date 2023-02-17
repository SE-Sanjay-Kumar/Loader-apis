package com.tms.loader.services.driver;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.tms.loader.entities.driver.Driver;
import com.tms.loader.entities.driver.DriverStatus;
import com.tms.loader.exceptions.CJpaSystemException;
import com.tms.loader.exceptions.ExceptionEnd;
import com.tms.loader.exceptions.ResourceNotFoundException;
import com.tms.loader.payloads.driver.DriverDto;
import com.tms.loader.repositories.driver.DriverRepo;
import com.tms.loader.repositories.driver.DriverStatusRepo;

@Service
public class DriverService {
	@Autowired
	private DriverRepo repo;
	@Autowired
	private DriverStatusRepo statusRepo;
	@Autowired
	ModelMapper mapper;
	public DriverDto addDriver(DriverDto dto) {
		Long statusId = dto.getStatusId();
		DriverStatus driverStatus = statusRepo.findById(statusId).orElseThrow(() -> new ResourceNotFoundException("Availability Status", "id", statusId));
		Driver driver = mapper.map(dto, Driver.class);
		driver.setStatus(driverStatus);
		try {
			repo.save(driver);
		}catch (JpaSystemException e) {
			throw new CJpaSystemException(dto.getUserName());
		}catch(Exception e) {
			throw new ExceptionEnd();
		}
		return mapper.map(driver, DriverDto.class);
	}
	public List<DriverDto> getDrivers() {
		List<Driver> drivers = repo.findAll();
		List<DriverDto> driverDtoList = drivers.stream()
		                                        .map(driver -> mapper.map(driver, DriverDto.class))
		                                        .collect(Collectors.toList());
		return driverDtoList;
	}
	public DriverDto getClientWithId(Long id){
		Driver driver = repo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Driver", "Id", id));
		return mapper.map(driver, DriverDto.class);
	}
	public List<DriverDto> getDriversWithStatusId(Long id) {
		
		List<Driver> driverStatusList = repo.findByStatusIdJoin(id);
		if (driverStatusList.size()==0) {
			throw new ResourceNotFoundException("Status", "Id",id);
		}

		List<DriverDto> driverDtoList= driverStatusList.stream()
			                                        .map(driver -> mapper.map(driver, DriverDto.class))
			                                        .collect(Collectors.toList());
		
		
		return driverDtoList;
	}
	
}
