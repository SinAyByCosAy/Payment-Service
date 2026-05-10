package dev.tanay.paymentservice.controllers;

import dev.tanay.paymentservice.services.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private PaymentService paymentService;
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }
    @PostMapping("/{orderId}")
    public String initiatePayment(@PathVariable String orderId){
        return paymentService.initiatePayment(orderId);
    }
}
