package com.tms.loader.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Entity
@AttributeOverrides({
	@AttributeOverride(name="id", column= @Column(name="admin_id")),
	@AttributeOverride(name="userName", column = @Column(name="admin_name"))
})
public class Admin extends User {
}
