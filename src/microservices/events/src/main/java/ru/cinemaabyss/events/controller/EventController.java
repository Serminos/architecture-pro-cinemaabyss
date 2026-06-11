package ru.cinemaabyss.events.controller;

import ru.cinemaabyss.events.kafka.EventProducer;
import ru.cinemaabyss.events.model.MovieEvent;
import ru.cinemaabyss.events.model.PaymentEvent;
import ru.cinemaabyss.events.model.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventProducer eventProducer;

    @GetMapping("/health")
    public Map<String, Boolean> health() {
        Map<String, Boolean> response = new HashMap<>();
        response.put("status", true);
        return response;
    }

    @PostMapping("/movie")
    public ResponseEntity<Map<String, Object>> createMovieEvent(@RequestBody MovieEvent event) {
        try {
            SendResult<String, Object> result = eventProducer.sendMovieEvent(event).get();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("partition", result.getRecordMetadata().partition());
            response.put("offset", result.getRecordMetadata().offset());
            response.put("event", event);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/user")
    public ResponseEntity<Map<String, Object>> createUserEvent(@RequestBody UserEvent event) {
        try {
            SendResult<String, Object> result = eventProducer.sendUserEvent(event).get();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("partition", result.getRecordMetadata().partition());
            response.put("offset", result.getRecordMetadata().offset());
            response.put("event", event);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<Map<String, Object>> createPaymentEvent(@RequestBody PaymentEvent event) {
        try {
            SendResult<String, Object> result = eventProducer.sendPaymentEvent(event).get();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("partition", result.getRecordMetadata().partition());
            response.put("offset", result.getRecordMetadata().offset());
            response.put("event", event);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
