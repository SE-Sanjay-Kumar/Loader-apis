package com.tms.loader.entities.driver;

import com.tms.loader.entities.User;
import com.tms.loader.entities.vehicle.Freight;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@AttributeOverrides({
	@AttributeOverride(name="id", column= @Column(name="driver_id")),
	@AttributeOverride(name="userName", column = @Column(name="driver_name"))
})
@ToString
public class Driver extends User {
	@Column(name="license_number", unique=true, nullable=false)
	private String licenseNumber;
	@Column(name="years_of_experience",nullable = false)
	private int yearsOfExperience;
	private int salary;
	@Column(name="food_cost")
	private int foodCost;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "dstatus_id", nullable=true)
	private DriverStatus status;
	@OneToOne
	@JoinColumn(name="vehicleId")
	private Freight vehicle;
	
//	added location
	private String location;
}
