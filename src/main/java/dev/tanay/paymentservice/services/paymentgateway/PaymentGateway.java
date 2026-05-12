package dev.tanay.paymentservice.services.paymentgateway;

public interface PaymentGateway {
    public String generatePaymentLink(String email, String phoneNo, String orderId, Long amount);
}
