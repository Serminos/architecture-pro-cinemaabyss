package ru.cinemaabyss.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class UserEvent {
    @JsonProperty("user_id")
    private int userId;
    private String username;
    private String email;
    private String action;
    private Instant timestamp;
}
