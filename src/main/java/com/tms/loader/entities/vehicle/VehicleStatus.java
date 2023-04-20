package com.tms.loader.entities.vehicle;

import com.tms.loader.entities.Status;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="vehicle_status")
@AttributeOverride(name="statusId", column=@Column(name="vstatus_id"))
@AllArgsConstructor
@Setter
@Getter
public class VehicleStatus extends Status {

}
