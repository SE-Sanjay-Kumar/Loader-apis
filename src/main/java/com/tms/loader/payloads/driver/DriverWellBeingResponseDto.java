package com.tms.loader.payloads.driver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DriverWellBeingResponseDto extends DriverWellBeingDto {
	private Long id;
	private String userName;
}
