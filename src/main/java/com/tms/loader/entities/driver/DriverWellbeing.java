package com.tms.loader.entities.driver;

import com.tms.loader.entities.WellBeing;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="Driver_Health")
@AttributeOverrides({
	@AttributeOverride(name="bodyTemperature", column= @Column(name="body_temperature")),
	@AttributeOverride(name="hasDisease", column = @Column(name="has_disease"))
	
})
@Setter
@Getter
@ToString
public class DriverWellbeing extends WellBeing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="health_id")
	private Long healthId;
	@Column(name="resting_hours")
	private int restingHours;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="driver_id", nullable=false, unique=true)
	private Driver driver;
}
