package com.tms.loader.entities.review;

import java.time.LocalDateTime;

import com.tms.loader.entities.order.Order;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long reviewId;
	protected Integer rating;
	protected String comment;
	@Column(name="review_date")
	protected LocalDateTime reviewDate;
	@OneToOne
    @JoinColumn(name = "order_id", unique = true)
    protected Order order;
}
