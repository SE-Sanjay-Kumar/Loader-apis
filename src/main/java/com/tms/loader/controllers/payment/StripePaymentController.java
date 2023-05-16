package com.tms.loader.controllers.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.tms.loader.payloads.payment.CustomerData;
import com.tms.loader.services.payment.StripeService;

@RestController
@RequestMapping("/api/pay")
public class StripePaymentController {
	@Autowired
	StripeService stripeService;
	
	@Value("${stripe.apikey}")
	private String apiKey;
	
	@PostMapping("/create-customer")
	public CustomerData index(@RequestBody CustomerData data) throws StripeException {
		Stripe.apiKey = apiKey;
		Map<String ,Object> params = new HashMap<>();
		params.put("name", data.getName());
		params.put("email", data.getEmail());
		
		Customer customer = Customer.create(params);
		data.setCustomerId(customer.getId());
		return data;
	}
	
	@PostMapping("/charge/{customerId}/{amount}/{description}")
	public ResponseEntity<String> chargeCustomer(
			@PathVariable("customerId") String customerId,
			@PathVariable("amount") int amount,
			@PathVariable("description") String description) {
		try {
			System.out.println("Inside payment");
			Charge charge = stripeService.chargeCreditCard(customerId, amount, description);
			return ResponseEntity.ok("Charge Successful: " + charge.getId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
