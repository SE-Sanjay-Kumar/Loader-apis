package com.tms.loader.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@AttributeOverrides({
	@AttributeOverride(name="id", column= @Column(name="client_id")),
	@AttributeOverride(name="userName", column = @Column(name="client_name"))
})
public class Client extends User {
	
	@Column(name="company_name")
	private String companyName;
}
