package com.tms.loader.services.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Service
public class StripeService {

	@Value("${stripe.apikey}")
	private String apiKey;
	
    public Charge chargeCreditCard(String token, double amount, String description) throws StripeException {
        Stripe.apiKey = apiKey;

        Map<String ,Object> chargeParams = new HashMap<>();
		chargeParams.put("amount", (int) (amount * 100)); // Stripe uses cents as currency
        chargeParams.put("currency", "usd");
        chargeParams.put("description", description);
        chargeParams.put("source", token);

        Charge charge = Charge.create(chargeParams);
        return charge;
    }

}