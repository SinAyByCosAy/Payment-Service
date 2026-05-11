package dev.tanay.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePaymentReqDto {
    private String email;
    private String phoneNo;
    private String orderId;
    private Long amount;
}
