package com.tms.loader.entities.vehicle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VehicleType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="type_id")
	private Integer typeId;
	@Column(name="type_name", unique=true, nullable=false)
	private String typeName;
	@Column(name="associated_cost")
	private Float cost;
//	@OneToMany(mappedBy = "vehicleId", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Freight> freights;
}
