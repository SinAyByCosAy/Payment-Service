package dev.tanay.paymentservice.paymentgateway;

import org.springframework.stereotype.Service;

@Service
public class RazorpayPaymentGateway implements PaymentGateway{
    @Override
    public String generatePaymentLink(){
        return "abc";
    }
}
