package com.tms.loader.payloads.payment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerData {
	public String name;
	public String email;
	public String customerId;
}
