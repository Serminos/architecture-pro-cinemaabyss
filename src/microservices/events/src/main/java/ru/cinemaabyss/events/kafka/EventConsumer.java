package ru.cinemaabyss.events.kafka;

import ru.cinemaabyss.events.model.MovieEvent;
import ru.cinemaabyss.events.model.PaymentEvent;
import ru.cinemaabyss.events.model.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumer {
    private static final Logger log = LoggerFactory.getLogger(EventConsumer.class);

    @KafkaListener(topics = "movie-events", groupId = "events-group")
    public void consumeMovieEvent(MovieEvent event) {
        log.info("Received movie event: {}", event);
    }

    @KafkaListener(topics = "user-events", groupId = "events-group")
    public void consumeUserEvent(UserEvent event) {
        log.info("Received user event: {}", event);
    }

    @KafkaListener(topics = "payment-events", groupId = "events-group")
    public void consumePaymentEvent(PaymentEvent event) {
        log.info("Received payment event: {}", event);
    }
}
