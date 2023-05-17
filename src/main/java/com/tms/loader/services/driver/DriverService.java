package com.tms.loader.services.driver;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tms.loader.configs.MYConstants;
import com.tms.loader.entities.Status;
import com.tms.loader.entities.driver.Driver;
import com.tms.loader.entities.driver.DriverStatus;
import com.tms.loader.entities.vehicle.Vehicle;
import com.tms.loader.entities.vehicle.VehicleCost;
import com.tms.loader.entities.vehicle.VehicleStatus;
import com.tms.loader.exceptions.ConstraintViolationExceptionHandler;
import com.tms.loader.exceptions.DataIntegrityExceptionHandler;
import com.tms.loader.exceptions.AllExceptionHandler;
import com.tms.loader.exceptions.ResourceNotFoundException;
import com.tms.loader.payloads.LoginUserDetailDto;
import com.tms.loader.payloads.StatusDto;
import com.tms.loader.payloads.driver.DriverDto;
import com.tms.loader.payloads.driver.DriverWithVehicleDto;
import com.tms.loader.payloads.driver.UpdateDriverDto;
import com.tms.loader.repositories.driver.DriverRepo;
import com.tms.loader.repositories.driver.DriverStatusRepo;
import com.tms.loader.repositories.vehicle.FreightRepo;
import com.tms.loader.services.vehicle.VehicleStatusService;

import jakarta.transaction.Transactional;

@Service
public class DriverService {
	@Autowired
	private DriverRepo repo;
	@Autowired
	private FreightRepo freightRepo;
	@Autowired
	private VehicleStatusService vstatusService;
	@Autowired
	private DriverStatusRepo statusRepo;
	@Autowired
	ModelMapper mapper;
	public DriverDto addDriver(DriverDto dto) {
		Status statusId = dto.getStatus();
		DriverStatus driverStatus = statusRepo.findById(statusId.getStatusId()).orElseThrow(() -> new ResourceNotFoundException("Availability Status", "id", statusId.getStatusId()));
		Driver driver = mapper.map(dto, Driver.class);
		driver.setStatus(driverStatus);
		try {
			repo.save(driver);
			StatusDto vstatus = vstatusService.getStatus(MYConstants.VEHICLE_ASSIGNED);
			VehicleStatus status = mapper.map(vstatus, VehicleStatus.class);
			VehicleCost cost= new VehicleCost();
			cost.setMaintenanceCost(dto.getVehicle().getCost().getMaintenanceCost());
			cost.setFuelCost( dto.getVehicle().getCost().getFuelCost());
			freightRepo.updateFreightById(status,cost,dto.getVehicle().getVehicleId());
		}catch (ConstraintViolationException e) {
			throw new ConstraintViolationExceptionHandler(dto.getUserName());
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityExceptionHandler();
		}catch(Exception e) {
			throw new AllExceptionHandler();
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
	public List<DriverWithVehicleDto> getDriversWithVehicle(){
		List<Object[]> driverJoinVehicle =  repo.findByVehicleIdJoin();
		List<DriverWithVehicleDto> driverWithVehicleDto = driverJoinVehicle.stream()
                .map( dto ->{
                	System.out.println("dto contains "+dto[0]);
                	Driver driver = (Driver) dto[0]; 
            		Vehicle vehicle = (Vehicle) dto[1];      
            		DriverWithVehicleDto resp = mapper.map(vehicle, DriverWithVehicleDto.class);		                                    		
            		mapper.map(driver, resp);
            		return resp;
                })
                .collect(Collectors.toList());
return driverWithVehicleDto;
	}
	// for update of driver data
	@Transactional
	public DriverDto updateDriver(UpdateDriverDto updateDriverDto, Long id) {
		System.out.println("data: "+updateDriverDto.getUserName()+updateDriverDto.getStatus());
		Optional<Driver> optionalDriver = repo.findById(id);
		if (optionalDriver.isPresent()) {
			try {
				System.out.println("I am here");
				repo.updateDriverById(updateDriverDto.getStatus(),updateDriverDto.getUserName() ,updateDriverDto.getLocation(),updateDriverDto.getFoodCost(), id);
				Driver updatedDriver = repo.findById(id).orElseThrow(
						
						()->  new ResourceNotFoundException("Driver","id", id));
				return mapper.map(updatedDriver, DriverDto.class);
			}catch (Exception e) {
	        	System.out.println(e.getLocalizedMessage()+e.getStackTrace());
	            throw new AllExceptionHandler();
	        }
			
		}else {
	        throw new ResourceNotFoundException("Driver", "id", id);
	    }
	}
	public LoginUserDetailDto authDriver(String username, String password) {
	    Driver driver = repo.findByuserName(username);
	    if (driver == null) {
	        // If no admin user with the given username exists, return false
	        return null;
	    } else {
	        // If an admin user with the given username exists, check if the password matches
	        if(driver.getPassword().equals(password)) {
	        	return mapper.map(driver, LoginUserDetailDto.class);
	        }return null;
	    }
	}

	
}
