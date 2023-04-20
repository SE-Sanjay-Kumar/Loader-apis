package com.tms.loader.entities.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLocation {
	@Column(name="pick_up",nullable = false)
	private String pickUp;
	@Column(name="drop_off",nullable = false)
	private String dropOff;
}
