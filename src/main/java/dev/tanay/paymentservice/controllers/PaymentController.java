package dev.tanay.paymentservice.controllers;

import dev.tanay.paymentservice.dtos.InitiatePaymentReqDto;
import dev.tanay.paymentservice.services.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private PaymentService paymentService;
    public PaymentController (PaymentService paymentService){
        this.paymentService = paymentService;
    }
    @PostMapping("/")
    public String initiatePayment(@RequestBody InitiatePaymentReqDto reqDto){
        //we won't actually receive this data from the client, we'll get it from the Order service via orderId
        return paymentService.initiatePayment(reqDto.getEmail(), reqDto.getPhoneNo(), reqDto.getOrderId(), reqDto.getAmount());
    }
}
