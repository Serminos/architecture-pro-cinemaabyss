package ru.cinemaabyss.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class PaymentEvent {
    @JsonProperty("payment_id")
    private int paymentId;
    @JsonProperty("user_id")
    private int userId;
    private double amount;
    private String status;
    private Instant timestamp;
    @JsonProperty("method_type")
    private String methodType;
}
