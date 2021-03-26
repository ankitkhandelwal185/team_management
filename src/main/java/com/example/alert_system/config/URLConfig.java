package com.example.alert_system.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="url")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class URLConfig {
    private String SmsURL;
}
