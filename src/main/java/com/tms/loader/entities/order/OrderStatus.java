package com.tms.loader.entities.order;

import com.tms.loader.entities.Status;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Table(name="order_status")
@AttributeOverride(name="statusId", column=@Column(name="ostatus_id"))
@Setter
@Getter
public class OrderStatus extends Status {
	public OrderStatus(Integer id, String status) {
		super(id, status);
	}
}
