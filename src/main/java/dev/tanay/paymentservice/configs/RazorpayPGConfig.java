package dev.tanay.paymentservice.configs;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayPGConfig {
    @Value("${razorpay.key.id}")
    private String razorpayKeyId;
    @Value("${razorpay.secret.key}")
    private String razorpaySecretKey;
    @Bean
    public RazorpayClient initializeRazorpayClient() throws RazorpayException {
        return new RazorpayClient(razorpayKeyId, razorpaySecretKey);
    }
}
