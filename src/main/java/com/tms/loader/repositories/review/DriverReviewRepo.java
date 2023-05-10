package com.tms.loader.repositories.review;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tms.loader.entities.review.DriverReview;

public interface DriverReviewRepo extends JpaRepository<DriverReview, Long>{
	DriverReview findByOrder(Long orderId);
}
