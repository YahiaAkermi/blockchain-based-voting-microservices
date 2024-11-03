package com.yahia.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

@SpringBootApplication
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }

    @Bean
    public RouteLocator yahiaOrgRouteConfig(RouteLocatorBuilder routeLocatorBuilder){

        return  routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/yahiaorg/vm-ms/**")
                        .filters(f -> f.rewritePath("/yahiaorg/vm-ms/(?<segment>.*)","/${segment}").
                                addResponseHeader("X-Response-Time", ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.RFC_1123_DATE_TIME)))
                        .uri("lb://VM-MS"))
                .route(p ->p.path("/yahiaorg/voting-ms/**")
                        .filters(f -> f.rewritePath("/yahiaorg/voting-ms/(?<segment>.*)","/${segment}").
                                addResponseHeader("X-Response-Time", ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.RFC_1123_DATE_TIME))
                                .circuitBreaker(config -> config.setName("VotingMsCircuitBreaker")
                                                      .setFallbackUri("forward:/contactSupport"))
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                                                    .setKeyResolver(userKeyResolver()))
                        )
                        .uri("lb://VOTING-MS")).build();

    }
    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(8)).build()).build());
    }

    @Bean
    KeyResolver userKeyResolver(){
        return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
                .defaultIfEmpty("Anonymous");
    }

    @Bean
    public RedisRateLimiter redisRateLimiter(){
        return new RedisRateLimiter(1,30,30);
    }


}
