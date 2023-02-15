package com.tms.loader.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
public class Status {
	@Id
	protected Integer statusId;
	@Column(unique = true, nullable = false)
	protected String status;
}
