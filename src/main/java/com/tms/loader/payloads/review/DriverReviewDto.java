package com.tms.loader.payloads.review;

import com.tms.loader.entities.driver.Driver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DriverReviewDto extends ReviewDto {
	private Driver driver;
}
