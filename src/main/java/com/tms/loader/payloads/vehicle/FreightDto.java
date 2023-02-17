package com.tms.loader.payloads.vehicle;


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
	private Long typeId;
}
