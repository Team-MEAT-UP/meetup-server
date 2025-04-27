package com.meetup.server.global.config;

import com.meetup.server.global.clients.google.place.GooglePlaceProperties;
import com.meetup.server.global.clients.kakao.local.KakaoLocalProperties;
import com.meetup.server.global.clients.kakao.mobility.KakaoMobilityProperties;
import com.meetup.server.global.clients.odsay.OdsayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {KakaoLocalProperties.class, OdsayProperties.class, KakaoMobilityProperties.class, GooglePlaceProperties.class})
public class ConfigurationPropertiesConfig {
}
