package com.seefoodquickly.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.model.Charge;

@Service
public class StripeService {

    @Value("sk_test_51JkGb9LiLrBml3YZ5PYRgJLaXWGpq6tB3Llmrzhjwgf3F3x5Z1sUe2vcJ3dzVxJ30HblSX3Sdnj4aEXsffVjGMeY00PgPTPOI6")
    private String API_SECRET_KEY;

    @Autowired
    public StripeService() {
        Stripe.apiKey = API_SECRET_KEY;
    }

    public Charge chargeNewCard(String token, double amount) throws Exception {
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount * 100));
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
}