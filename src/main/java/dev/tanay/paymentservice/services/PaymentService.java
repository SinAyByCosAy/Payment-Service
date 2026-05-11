package dev.tanay.paymentservice.services;

import dev.tanay.paymentservice.paymentgateway.PaymentGateway;
import dev.tanay.paymentservice.paymentgateway.PaymentGatewayChooserStrategy;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;
    public PaymentService(PaymentGatewayChooserStrategy paymentGatewayChooserStrategy){
        this.paymentGatewayChooserStrategy = paymentGatewayChooserStrategy;
    }
    public String initiatePayment(String orderId){
        // assuming here we make a call to Order Service(separate service) to get Order details for the orderId
        // Order order = orderService.getOrderDetails(orderId);
        // Long amt = order.getAmount();
        Long amount = 1010L;
        PaymentGateway paymentGateway = paymentGatewayChooserStrategy.getBestPaymentGateway();
        return paymentGateway.generatePaymentLink();
    }
}
