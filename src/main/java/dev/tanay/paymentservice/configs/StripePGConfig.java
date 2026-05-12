package dev.tanay.paymentservice.configs;

import com.stripe.StripeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripePGConfig {
    @Value("${stripe.key.secret}")
    private String stripeSecretKey;
    @Bean
    public StripeClient initializeStripeClient(){
        return new StripeClient(stripeSecretKey);
    }
}
