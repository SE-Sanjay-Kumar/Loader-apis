package com.tms.loader.entities;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class WellBeing {
	protected float bodyTemperature;
	protected boolean hasDisease;
	@Column(nullable = true)
	protected String disease;
}
