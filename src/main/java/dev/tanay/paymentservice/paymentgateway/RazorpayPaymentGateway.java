package dev.tanay.paymentservice.paymentgateway;

import com.razorpay.PaymentLink;
import dev.tanay.paymentservice.configs.RazorpayConfig;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class RazorpayPaymentGateway implements PaymentGateway{
    private RazorpayClient razorpayClient;

    public RazorpayPaymentGateway(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String generatePaymentLink(){

    }
}
