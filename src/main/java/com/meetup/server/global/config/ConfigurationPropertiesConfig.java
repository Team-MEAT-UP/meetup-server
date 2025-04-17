package com.meetup.server.global.config;

import com.meetup.server.auth.application.KakaoAuthProperties;
import com.meetup.server.global.support.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {KakaoAuthProperties.class, JwtProperties.class})
public class ConfigurationPropertiesConfig {
}
