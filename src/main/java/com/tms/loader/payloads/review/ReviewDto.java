package com.tms.loader.payloads.review;

import java.util.Date;

import com.tms.loader.entities.order.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class ReviewDto {
	protected Long reviewId;
	protected Integer rating;
	protected String comment;
	protected Date reviewDate;
	protected Order order;
}
