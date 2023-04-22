package com.tms.loader.payloads.vehicle;

import com.tms.loader.entities.vehicle.VehicleType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTypeRespDto {
	private VehicleType type;
}
