package com.aat_projects.code_review.actuator;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
/**
 * HealthIndicator for external API reachability check.
 * Blocks on WebClient—prefer ReactiveHealthIndicator in production.
 */

@Component
public class ApiHealthIndicator implements HealthIndicator {

    private final WebClient webClient = WebClient.create();
    @Override
    public  Health health() {
        try{
             webClient.get()
                     .uri("https://api.example.com/health")// Externalize URI
                     .retrieve()
                     .toBodilessEntity()
                     .block();// Blocking call;

             return Health.up().withDetail("API","Reachable").build();
            }catch (Exception e){
            return Health.down().withDetail("API","Not reachable").build();
        }
    }
}
