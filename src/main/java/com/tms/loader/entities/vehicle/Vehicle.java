package com.tms.loader.entities.vehicle;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
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
	@Column(name="min_weightcarry")
	protected Long minWeightCarry;
	@Column(name="max_sizecarry")
	protected Long maxSizeCarry;
	protected float mileage;
	@Column(name="plate_no", unique= true, nullable=false)
	protected String plateNo;
	@Embedded
	protected VehicleCost cost;
}