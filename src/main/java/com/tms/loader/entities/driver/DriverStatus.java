package com.tms.loader.entities.driver;

import com.tms.loader.entities.Status;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AttributeOverride(name="statusId", column=@Column(name="dstatus_id"))
@AllArgsConstructor
@Setter
@Getter
public class DriverStatus extends Status {
	
}
