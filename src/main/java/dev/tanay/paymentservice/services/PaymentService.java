package dev.tanay.paymentservice.services;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public String initiatePayment(String orderId){
        // assuming here we make a call to Order Service(separate service) to get Order details for the orderId
        // Order order = orderService.getOrderDetails(orderId);
        // Long amt = order.getAmount();
        Long amount = 1010L;
        return "Hello: " + orderId;
    }
}
