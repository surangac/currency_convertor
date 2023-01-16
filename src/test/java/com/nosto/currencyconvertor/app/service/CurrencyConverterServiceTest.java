package com.nosto.currencyconvertor.app.service;

import com.nosto.currencyconvertor.app.dto.CurrencyConverterResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyConverterServiceTest {

    @Mock
    SymbolService symbolService;

    @Mock
    ExchangeService exchangeService;

    CurrencyConverterService currencyConverterService;

    @BeforeEach
    void setUp() {
        currencyConverterService = new CurrencyConverterService(exchangeService, symbolService);
    }

    @Test
    void currencyConvertor_testInvalidSource() {
        String source = "abc";
        String target = "GBP";
        double value = 10;

        when(symbolService.isValidSymbol(source)).thenReturn(false);

        CurrencyConverterResponseDto responseDto = currencyConverterService.currencyConvertor(source, target, value);
        assertEquals("Invalid Source Currency", responseDto.getMessage());
        assertEquals(-1, responseDto.getStatus());
    }

    @Test
    void currencyConvertor_testInvalidTarget() {
        String source = "EUR";
        String target = "xyz";
        double value = 10;

        when(symbolService.isValidSymbol(source)).thenReturn(true);
        when(symbolService.isValidSymbol(target)).thenReturn(false);

        CurrencyConverterResponseDto responseDto = currencyConverterService.currencyConvertor(source, target, value);
        assertEquals("Invalid Target Currency", responseDto.getMessage());
        assertEquals(-1, responseDto.getStatus());
    }

    @Test
    void currencyConvertor_testNullValues() {

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
            () -> currencyConverterService.currencyConvertor("", "", 10.0));

        Assertions.assertEquals("Source or Target can't be empty", exception.getMessage());
    }

    @Test
    void currencyConvertor_testAllSuccess() {

        String source = "EUR";
        String target = "GBP";
        double value = 10;

        when(symbolService.isValidSymbol(source)).thenReturn(true);
        when(symbolService.isValidSymbol(target)).thenReturn(true);
        when(exchangeService.getExchangeRate(source, target)).thenReturn(5.2);

        CurrencyConverterResponseDto responseDto = currencyConverterService.currencyConvertor(source, target, value);
        assertEquals(5.2, responseDto.getRate());
        assertEquals(1, responseDto.getStatus());
        assertEquals(52, responseDto.getResult());

        verify(symbolService, times(2)).isValidSymbol(any());
    }
}