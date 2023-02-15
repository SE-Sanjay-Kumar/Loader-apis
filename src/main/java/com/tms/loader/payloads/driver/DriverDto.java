package com.tms.loader.payloads.driver;

import com.tms.loader.payloads.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DriverDto extends UserDto{
	private String licenseNumber;
	private int yearsOfExperience;
	private int salary;
	private int foodCost;
	private Long statusId;
}
