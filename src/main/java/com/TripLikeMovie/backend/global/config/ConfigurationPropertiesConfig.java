package com.TripLikeMovie.backend.global.config;

import com.TripLikeMovie.backend.global.property.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(JwtProperties.class)
@Configuration
public class ConfigurationPropertiesConfig { }
