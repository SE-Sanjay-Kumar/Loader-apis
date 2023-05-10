package com.tms.loader.repositories.review;


import org.springframework.data.jpa.repository.JpaRepository;

import com.tms.loader.entities.order.Order;
import com.tms.loader.entities.review.ClientReview;

public interface ClientReviewRepo extends JpaRepository<ClientReview, Long> {
	ClientReview findByOrder(Order order);
}
