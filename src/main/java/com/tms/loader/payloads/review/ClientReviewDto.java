package com.tms.loader.payloads.review;

import com.tms.loader.entities.Client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ClientReviewDto extends ReviewDto {
	private Client client;
}
