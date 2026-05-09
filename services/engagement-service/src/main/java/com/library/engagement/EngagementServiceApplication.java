package com.library.engagement;

import com.library.engagement.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class EngagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EngagementServiceApplication.class, args);
    }
}
