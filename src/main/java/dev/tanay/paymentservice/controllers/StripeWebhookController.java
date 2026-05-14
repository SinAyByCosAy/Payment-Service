package dev.tanay.paymentservice.controllers;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StripeWebhookController {

    // Pull this from your application.properties
    // If testing locally using Stripe CLI, use the secret it gives you in the terminal
    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping("/stripeWebhook")
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,                   // CRITICAL: Keep as raw String
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event = null;

        try {
            // 1. Verify the signature and construct the Event
            // This ensures the request actually came from Stripe
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

        } catch (SignatureVerificationException e) {
            // Invalid signature
            System.out.println("⚠️ Webhook error while validating signature: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signature Verification Failed");

        } catch (Exception e) {
            // Invalid payload or other parsing errors
            System.out.println("⚠️ Webhook error parsing payload: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payload Parsing Failed");
        }

        // 2. Deserialize the nested object inside the event
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;

        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            // Deserialization failed, probably due to an API version mismatch.
            System.out.println("⚠️ Webhook error: Deserialization failed.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deserialization Failed");
        }

        // 3. Handle the specific event type - we can use strategy to handle the different cases here
        switch (event.getType()) {
            case "checkout.session.completed":
                // Cast the StripeObject to the specific type you are expecting
                com.stripe.model.checkout.Session session = (com.stripe.model.checkout.Session) stripeObject;
                System.out.println("Checkout Session Completed for Order ID: " + session.getClientReferenceId());

                // TODO: Fulfill the purchase, mark order as PAID in your database
                // handleCheckoutSessionCompleted(session);
                break;

            case "payment_intent.succeeded":
                PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                System.out.println("PaymentIntent succeeded for amount: " + paymentIntent.getAmount());
                break;

            case "payment_intent.payment_failed":
                PaymentIntent failedIntent = (PaymentIntent) stripeObject;
                System.out.println("Payment failed: " + failedIntent.getLastPaymentError().getMessage());
                // TODO: Update order status to FAILED or PAYMENT_ACTION_REQUIRED
                break;

            // ... handle other event types as needed
            default:
                System.out.println("Unhandled event type: " + event.getType());
        }

        // 4. Quickly return a 200 OK response
        // This tells Stripe we received it successfully so they stop retrying
        return ResponseEntity.ok("Success");
    }
}