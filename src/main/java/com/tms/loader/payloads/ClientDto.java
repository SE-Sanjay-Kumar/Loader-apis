package com.tms.loader.payloads;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientDto extends UserDto {
	private String companyName;
}
