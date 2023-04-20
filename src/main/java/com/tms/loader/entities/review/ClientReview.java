package com.tms.loader.entities.review;

import com.tms.loader.entities.Client;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "client_reviews")
@Setter
@Getter
@AttributeOverride(name="reviewId", column=@Column(name="client_reviewid"))
public class ClientReview extends Review {
	@OneToOne
	@JoinColumn(name = "client_id")
	private Client client;
	public Long getClientReviewId() {
	      return this.reviewId;
	}
}
