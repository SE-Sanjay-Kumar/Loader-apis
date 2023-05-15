package com.tms.loader.payloads.vehicle;


import com.tms.loader.entities.vehicle.VehicleStatus;
import com.tms.loader.entities.vehicle.VehicleType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FreightDto {
	private Long vehicleId;
	private String name;
	private String plateNo;
	private float maintenanceCost;
	private float fuelCost;
	private Long maxWeightCarry;
	private float mileage;
	private VehicleType type;
	private VehicleStatus status;
	private Long maxSizeCarry;
	private Long minWeightCarry;
	
}
