package com.tms.loader.payloads;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AdminDto extends UserDto{

	public AdminDto(Long id, String userName, String password, String phoneNumber, Long cnic) {
		super(id, userName, password, phoneNumber, cnic);
	}

}
