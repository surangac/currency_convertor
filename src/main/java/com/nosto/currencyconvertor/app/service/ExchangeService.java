package com.nosto.currencyconvertor.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Configuration
public class ExchangeService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String symbolUrl;
    private final String converterUrl;
    private final ObjectMapper objectMapper;

    public ExchangeService(
        RestTemplate restTemplate,
        @Value("${api-key}") String apiKey,
        @Value("${exchange.api.symbol-url}") String symbolUrl,
        @Value("${exchange.api.converter-url}") String converterUrl,
        ObjectMapper objectMapper
    ) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.symbolUrl = symbolUrl;
        this.converterUrl = converterUrl;
        this.objectMapper = objectMapper;
    }

    public String loadSymbols() {

        ResponseEntity<String> response = restTemplate.exchange(
            symbolUrl,
            HttpMethod.GET,
            getHttpEntity(),
            String.class
        );

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            // log messaage
            return null;
        }
    }

    @Cacheable(cacheNames = "exchangeRates", key = "{#sourceCurrency, #targetCurrency}", cacheManager = "exchangeRatesCacheManager")
    public double getExchangeRate(String sourceCurrency, String targetCurrency) {

        try {
            String response = callExchangeService(sourceCurrency, targetCurrency);
            if(response != null) {
                Map<String, Object> responseData = objectMapper.readValue(response, Map.class);
                return Double.parseDouble(((Map<Object, Object>) responseData.get("rates")).get(targetCurrency).toString());
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String  callExchangeService(String from, String to) {
        Map<String, String> params = new HashMap<>();
        params.put("base", from);
        params.put("symbols", to);

        ResponseEntity<String> response = restTemplate.exchange(
            converterUrl,
            HttpMethod.GET,
            getHttpEntity(),
            String.class,
            params
        );

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            // log messaage
            return null;
        }
    }

    private HttpEntity getHttpEntity() {
        return new HttpEntity(getHeader());
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", apiKey);
        return headers;
    }


}
