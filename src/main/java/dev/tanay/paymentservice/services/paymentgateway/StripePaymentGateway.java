package dev.tanay.paymentservice.services.paymentgateway;

import org.springframework.stereotype.Service;

@Service
public class StripePaymentGateway implements PaymentGateway{
    @Override
    public String generatePaymentLink(String email, String phoneNo, String orderId, Long amount){
        return null;
    }
}
