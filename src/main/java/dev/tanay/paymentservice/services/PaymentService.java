package dev.tanay.paymentservice.services;

import dev.tanay.paymentservice.services.paymentgateway.PaymentGateway;
import dev.tanay.paymentservice.services.paymentgateway.PaymentGatewayChooserStrategy;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    // This will get order details from the order service based on the order id
    // then select the best available Payment Gateway
    // make a call to PG to get the link for the payment
    // return back the payment link

    private PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;
    public PaymentService(PaymentGatewayChooserStrategy paymentGatewayChooserStrategy){
        this.paymentGatewayChooserStrategy = paymentGatewayChooserStrategy;
    }
    public String initiatePayment(String email, String phoneNo, String orderId, Long amount){
        PaymentGateway pg = paymentGatewayChooserStrategy.getBestPaymentGateway();
        return null;
    }
}
