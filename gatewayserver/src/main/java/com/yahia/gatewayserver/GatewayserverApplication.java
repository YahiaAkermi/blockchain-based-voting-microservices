package com.yahia.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
                                addResponseHeader("X-Response-Time", ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.RFC_1123_DATE_TIME)))
                        .uri("lb://VOTING-MS")).build();

    }

}
