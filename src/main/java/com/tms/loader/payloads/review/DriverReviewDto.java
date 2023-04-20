package com.tms.loader.payloads.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DriverReviewDto extends ReviewDto {
	private Long driverId;
}
