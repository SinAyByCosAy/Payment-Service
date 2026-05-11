package dev.tanay.paymentservice.services;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    // This will get order details from the order service based on the order id
    // then select the best available Payment Gateway
    // make a call to PG to get the link for the payment
    // return back the payment link
    public String initiatePayment(){
        return null;
    }
}
