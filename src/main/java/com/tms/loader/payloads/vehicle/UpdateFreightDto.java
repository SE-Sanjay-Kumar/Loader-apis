package com.tms.loader.payloads.vehicle;

import com.tms.loader.entities.vehicle.VehicleStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateFreightDto {
	private float maintenanceCost;
	private float fuelCost;
	private VehicleStatus status;
}
