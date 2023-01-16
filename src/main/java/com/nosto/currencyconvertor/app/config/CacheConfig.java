package com.nosto.currencyconvertor.app.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CacheConfig {
    @Bean
    public CacheManager exchangeRatesCacheManager() {
        final CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        final var settings = Caffeine.newBuilder()
            .recordStats()
            .expireAfterWrite(Duration.ofHours(24));

        cacheManager.setCaffeine(settings);
        return cacheManager;
    }
}
