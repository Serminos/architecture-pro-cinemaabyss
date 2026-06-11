package ru.cinemaabyss.proxy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${services.monolith-url}")
    private String monolithUrl;

    @Value("${services.movies-service-url}")
    private String moviesServiceUrl;

    @Value("${services.events-service-url}")
    private String eventsServiceUrl;

    @Value("${feature.movies-migration-enabled}")
    private boolean moviesMigrationEnabled;

    @Value("${feature.movies-migration-percent}")
    private int moviesMigrationPercent;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routes = builder.routes();

        // Маршрут для событий всегда в events-service
        routes.route("events", r -> r
                .path("/api/events/**")
                .uri(eventsServiceUrl));

        if (moviesMigrationEnabled) {
            // Процентное распределение для /api/movies
            int microWeight = moviesMigrationPercent;
            int monolithWeight = 100 - microWeight;

            routes.route("movies_micro", r -> r
                            .path("/api/movies/**")
                            .and().weight("movies-group", microWeight)
                            .uri(moviesServiceUrl))
                    .route("movies_monolith", r -> r
                            .path("/api/movies/**")
                            .and().weight("movies-group", monolithWeight)
                            .uri(monolithUrl));
        } else {
            // Если флаг выключен, все /api/movies идут в монолит
            routes.route("movies_monolith", r -> r
                    .path("/api/movies/**")
                    .uri(monolithUrl));
        }

        // Все остальные запросы (users, payments, subscriptions и т.д.) в монолит
        routes.route("default", r -> r
                .path("/**")
                .uri(monolithUrl));

        return routes.build();
    }
}