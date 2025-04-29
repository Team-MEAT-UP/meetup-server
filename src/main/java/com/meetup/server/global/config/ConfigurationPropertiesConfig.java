package com.meetup.server.global.config;

import com.meetup.server.global.support.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {JwtProperties.class})
public class ConfigurationPropertiesConfig {
}
