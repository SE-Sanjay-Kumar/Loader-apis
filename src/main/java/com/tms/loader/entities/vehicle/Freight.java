package com.tms.loader.entities.vehicle;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@AttributeOverride(name="vehicleId", column = @Column(name = "vehicle_id"))
public class Freight extends Vehicle {
	
	@ManyToOne
	@JoinColumn(name = "typeId")
	private VehicleType type;
}	
