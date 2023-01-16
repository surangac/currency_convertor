package com.nosto.currencyconvertor.app.controller;

import com.nosto.currencyconvertor.app.dto.CurrencyConverterResponseDto;
import com.nosto.currencyconvertor.app.service.CurrencyConverterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin()
@RequestMapping("api")
public class CurrencyConverterController {

    private CurrencyConverterService converterService;

    public CurrencyConverterController(CurrencyConverterService converterService) {
        this.converterService =  converterService;
    }

    @GetMapping("/currency-converter")
    public ResponseEntity<CurrencyConverterResponseDto> getCurrencyConvert(
        @RequestParam("source") String sourceCurrency,
        @RequestParam("target") String targetCurrency,
        @RequestParam(value = "amount", defaultValue = "0") String monetaryValue,
        HttpServletResponse response
    ) {
        long start = System.currentTimeMillis();

        CurrencyConverterResponseDto responseDto = converterService.currencyConvertor(
            sourceCurrency.trim().toUpperCase(),
            targetCurrency.trim().toUpperCase(),
            Double.parseDouble(monetaryValue)
        );

        long end = System.currentTimeMillis();
        long duration = end - start;
        response.addHeader("Server-Timing", "currency-converter;dur=" + duration + ";desc=Currency Converter");

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
