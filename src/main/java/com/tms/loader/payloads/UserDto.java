package com.tms.loader.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public abstract class UserDto {
	protected Long id;
	protected String userName;
	protected String password;
	protected String phoneNumber;
	protected Long cnic;
}
