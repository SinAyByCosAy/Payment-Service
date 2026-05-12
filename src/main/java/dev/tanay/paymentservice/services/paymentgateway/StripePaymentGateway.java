package dev.tanay.paymentservice.services.paymentgateway;

import com.stripe.StripeClient;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentGateway implements PaymentGateway {

    private final StripeClient stripeClient;
    public StripePaymentGateway(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

    @Override
    public String generatePaymentLink(String email, String phoneNo, String orderId, Long amount) {
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://google.com/")
                    .setCancelUrl("https://google.com/")
                    .setCustomerEmail(email)
                    .setClientReferenceId(orderId)
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("INR")
                                                    .setUnitAmount(amount)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Payment for policy no order#:" + orderId)
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            // Use the injected client instance to create the session
            Session session = stripeClient.checkout().sessions().create(params);

            return session.toString();

        } catch (Exception e) {
            System.out.println(e);
            return "Oops! Something went wrong";
        }
    }
}