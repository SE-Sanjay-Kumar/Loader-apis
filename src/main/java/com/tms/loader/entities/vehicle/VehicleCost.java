package com.tms.loader.entities.vehicle;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class VehicleCost {
	@Column(name="maintenance_cost")
	private float maintenanceCost;
	@Column(name = "fuel_cost")
	private float fuelCost;
}
