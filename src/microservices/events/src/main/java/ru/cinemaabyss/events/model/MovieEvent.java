package ru.cinemaabyss.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class MovieEvent {
    @JsonProperty("movie_id")
    private int movieId;
    private String title;
    private String action;
    @JsonProperty("user_id")
    private Integer userId;
    private Double rating;
    private String[] genres;
    private String description;
}
