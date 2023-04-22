package com.tms.loader.entities.vehicle;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
public abstract class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long vehicleId;
	@Column(name="vehicle_name", unique=true, nullable=false)
	protected String name;
	@Column(name="max_weightcarry")
	protected Long maxWeightCarry;
	protected float mileage;
	@Column(name="plate_no", unique= true, nullable=false)
	protected String plateNo;
	@Embedded
	protected VehicleCost cost;
}