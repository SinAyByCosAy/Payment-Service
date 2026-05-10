package dev.tanay.paymentservice.services;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public String initiatePayment(String orderId){
        return "Hello: " + orderId;
    }
}
