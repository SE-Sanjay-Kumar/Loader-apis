package com.tms.loader.entities.order;


import com.tms.loader.entities.Client;
import com.tms.loader.entities.driver.Driver;
import com.tms.loader.entities.payment.Payment;
import com.tms.loader.entities.review.ClientReview;
import com.tms.loader.entities.review.DriverReview;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id")
	private Long orderId;
	@Column(name="number_of_labors")
	private Integer noOfLabors;
	@Column(name="total_weight")
	private float totalWeight;
	@Column(name="total_size")
	private float totalSize;
	@Column(name="is_fragile")
	private boolean fragility;
	private float price;
	@ManyToOne(optional = true)
	@JoinColumn(name = "ostatus_id")
	private OrderStatus status;
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;
	@ManyToOne(optional = true)
	@JoinColumn(name="driver_id")
	private Driver driver;
	@ManyToOne(optional = true)
	@JoinColumn(name="payment_id")
	private Payment payment;
	@Embedded
	private OrderLocation orderLocation;
	@Embedded
	private OrderSchedule orderSchedule;
	
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private ClientReview clientReview;
    
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private DriverReview driverReview;
}
