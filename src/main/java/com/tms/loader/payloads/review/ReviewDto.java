package com.tms.loader.payloads.review;

import java.time.LocalDateTime;

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
	protected LocalDateTime reviewDate;
	protected Long orderId;
}