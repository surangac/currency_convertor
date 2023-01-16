package com.nosto.currencyconvertor.app.service;

import com.nosto.currencyconvertor.app.dto.CurrencyConverterResponseDto;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConverterService {

    private final ExchangeService exchangeService;
    private final SymbolService symbolService;

    public CurrencyConverterService(
        ExchangeService exchangeService,
        SymbolService symbolService
    ) {

        this.exchangeService = exchangeService;
        this.symbolService = symbolService;
    }

    public CurrencyConverterResponseDto currencyConvertor(String sourceCurrency, String targetCurrency, Double monetaryValue) {
        if(sourceCurrency.isEmpty() || targetCurrency.isEmpty()) {
            throw new IllegalArgumentException("Source or Target can't be empty");
        }
        CurrencyConverterResponseDto responseDto = new CurrencyConverterResponseDto();
        responseDto.setSourceCurrency(sourceCurrency);
        responseDto.setTargetCurrency(targetCurrency);
        responseDto.setMonetaryValue(monetaryValue);

        if(!symbolService.isValidSymbol(sourceCurrency)) {
            responseDto.setStatus(-1);
            responseDto.setMessage("Invalid Source Currency");
            return responseDto;
        }

        if(!symbolService.isValidSymbol(targetCurrency)) {
            responseDto.setStatus(-1);
            responseDto.setMessage("Invalid Target Currency");
            return responseDto;
        }
        double rate =  exchangeService.getExchangeRate(sourceCurrency, targetCurrency);

        double result = rate * monetaryValue;

        responseDto.setRate(rate);
        responseDto.setResult(result);
        responseDto.setStatus(1);

        return responseDto;
    }
}
