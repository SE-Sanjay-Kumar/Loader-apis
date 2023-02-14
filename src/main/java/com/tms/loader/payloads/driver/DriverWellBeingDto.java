package com.tms.loader.payloads.driver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DriverWellBeingDto {
	private Long healthId;
	private float bodyTemperature;
	private boolean hasDisease;
	private String disease;
	private int restingHours;
}
