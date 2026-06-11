package ru.cinemaabyss.events.kafka;

import ru.cinemaabyss.events.model.MovieEvent;
import ru.cinemaabyss.events.model.PaymentEvent;
import ru.cinemaabyss.events.model.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EventProducer {
    private static final String MOVIE_TOPIC = "movie-events";
    private static final String USER_TOPIC = "user-events";
    private static final String PAYMENT_TOPIC = "payment-events";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public CompletableFuture<SendResult<String, Object>> sendMovieEvent(MovieEvent event) {
        return kafkaTemplate.send(MOVIE_TOPIC, String.valueOf(event.getMovieId()), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.err.println("Failed to send movie event: " + ex.getMessage());
                    } else {
                        System.out.println("Movie event sent successfully: offset="
                                + result.getRecordMetadata().offset());
                    }
                });
    }

    public CompletableFuture<SendResult<String, Object>> sendUserEvent(UserEvent event) {
        return kafkaTemplate.send(USER_TOPIC, String.valueOf(event.getUserId()), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.err.println("Failed to send user event: " + ex.getMessage());
                    }
                });
    }

    public CompletableFuture<SendResult<String, Object>> sendPaymentEvent(PaymentEvent event) {
        return kafkaTemplate.send(PAYMENT_TOPIC, String.valueOf(event.getPaymentId()), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.err.println("Failed to send payment event: " + ex.getMessage());
                    }
                });
    }
}