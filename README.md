# Payment Service

A Spring Boot project built to understand how payment gateway integrations work in a backend service.

The project integrates two payment gateway providers: Stripe and Razorpay. It explores how a backend service can abstract multiple payment providers behind a common interface and initiate payments without tightly coupling the core payment flow to a specific gateway.

## What This Project Demonstrates

- Designing a common payment gateway abstraction
- Integrating Stripe Checkout Sessions
- Integrating Razorpay Payment Links
- Selecting a payment gateway through a strategy layer
- Keeping provider-specific logic isolated from the main payment service
- Handling Stripe webhook events with signature verification
- Understanding how payment initiation and callback/webhook flows work in real-world systems

## Core Idea

The service exposes a payment initiation flow where the application receives basic order and customer details, chooses a payment gateway, and delegates payment-link creation to the selected provider.

The main service does not directly depend on Stripe or Razorpay-specific implementation details. Instead, both providers implement a common `PaymentGateway` contract.

This keeps the payment flow extensible and makes it easier to add more gateways in the future.

## Payment Flow

1. A payment request is received by the backend.
2. The payment service asks the gateway selection strategy to choose a provider.
3. The selected gateway creates a payment link or checkout session.
4. The generated payment URL is returned to the client.
5. Stripe webhook events are received separately and verified using Stripe’s signature verification mechanism.

## Gateway Abstraction

The project uses a `PaymentGateway` interface to define a common contract for payment providers.

Stripe and Razorpay have different APIs, request formats, and response structures, but the rest of the application interacts with them through the same abstraction.

This helped me understand how provider-specific integrations can be hidden behind a clean service boundary.

## Stripe Integration

The Stripe implementation creates a Checkout Session and returns the hosted checkout URL.

This helped me understand:

- How Stripe Checkout Sessions are created
- How success and cancel URLs are configured
- How Stripe represents payment sessions
- How webhook signatures are verified before processing events

## Razorpay Integration

The Razorpay implementation creates a Payment Link and returns the generated short URL.

This helped me understand:

- How Razorpay Payment Links are created
- How customer details are passed to Razorpay
- How payment amount and currency are represented
- How Razorpay’s Java SDK structures payment-link creation

## Webhook Handling

The project includes a Stripe webhook endpoint that verifies incoming webhook requests using the Stripe webhook secret.

The webhook controller currently handles events such as:

- `checkout.session.completed`
- `payment_intent.succeeded`
- `payment_intent.payment_failed`

This helped me understand why payment status should not be trusted only from frontend redirects and why backend webhook confirmation is important.

## Key Learning

The biggest learning from this project was understanding that payment integration is not just about calling a payment gateway API.

A production-grade payment system needs clear boundaries between:

- order creation
- payment initiation
- gateway-specific logic
- payment status tracking
- webhook verification
- failure handling
- reconciliation

This project focuses mainly on the payment initiation and webhook verification parts, while keeping the design open for future improvements like persistence, idempotency, order-service integration, and proper payment status management.

## Future Scope

- Persist payment attempts and statuses
- Add Razorpay webhook handling
- Add idempotency support
- Replace random gateway selection with configurable routing
- Integrate with an order service
- Add structured error handling
- Track complete payment lifecycle states