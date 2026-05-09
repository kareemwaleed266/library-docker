package com.library.circulation;

import com.library.circulation.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients
@EnableJpaAuditing
@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class CirculationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CirculationServiceApplication.class, args);
    }
}
