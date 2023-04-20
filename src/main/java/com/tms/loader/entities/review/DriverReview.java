package com.tms.loader.entities.review;

import com.tms.loader.entities.driver.Driver;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "driver_reviews")
@Setter
@Getter
@AttributeOverride(name="reviewId", column=@Column(name="driver_reviewid"))
public class DriverReview extends Review {
	@OneToOne
	@JoinColumn(name = "driver_id")
	private Driver driver;
	public Long getDriverReviewId() {
        return this.reviewId;
    }
}
