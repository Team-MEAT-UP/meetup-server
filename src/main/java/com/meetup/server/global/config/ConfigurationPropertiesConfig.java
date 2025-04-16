package com.meetup.server.global.config;

import com.meetup.server.member.application.KakaoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = KakaoProperties.class)
public class ConfigurationPropertiesConfig {
}
