package com.tms.loader.payloads.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTypeRespDto {
	private Integer typeId;
	private String typeName;
	private Float cost;
}
