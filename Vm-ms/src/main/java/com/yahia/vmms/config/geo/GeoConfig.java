package com.yahia.vmms.config.geo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GeoConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}





