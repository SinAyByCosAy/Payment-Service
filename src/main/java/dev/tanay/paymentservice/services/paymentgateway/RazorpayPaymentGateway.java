package dev.tanay.paymentservice.services.paymentgateway;

import com.razorpay.PaymentLink;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import com.razorpay.RazorpayClient;

import java.time.Instant;

@Service
public class RazorpayPaymentGateway implements PaymentGateway{
    private RazorpayClient razorpayClient;
    public RazorpayPaymentGateway(RazorpayClient razorpayClient){
        this.razorpayClient = razorpayClient;
    }
    @Override
    public String generatePaymentLink(String email, String phoneNo, String orderId, Long amount){
        try {
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("accept_partial", false);
            paymentLinkRequest.put("expire_by", Instant.now().plusSeconds(1050).getEpochSecond());
            paymentLinkRequest.put("reference_id", orderId);
            paymentLinkRequest.put("description", "Payment for policy no order#:"+orderId);
            JSONObject customer = new JSONObject();
            customer.put("name", "Tanay");
            customer.put("contact", phoneNo);
            customer.put("email", email);
            paymentLinkRequest.put("customer", customer);
            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);
            paymentLinkRequest.put("reminder_enable", true);
            JSONObject notes = new JSONObject();
            paymentLinkRequest.put("notes", notes);
            paymentLinkRequest.put("callback_url", "https://google.com/");
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
            return payment.toString();
        }catch (Exception e){
            System.out.println(e);
            return "Oops! Something went wrong";
        }
    }
}
