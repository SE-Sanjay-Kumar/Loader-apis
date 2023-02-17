package com.tms.loader.payloads.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTypeRespDto extends VehicleTypeDto {
	private Long typeId;
}
