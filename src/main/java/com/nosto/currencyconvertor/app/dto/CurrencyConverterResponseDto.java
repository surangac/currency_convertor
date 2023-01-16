package com.nosto.currencyconvertor.app.dto;

public class CurrencyConverterResponseDto {
    private String sourceCurrency;
    private String targetCurrency;
    private double monetaryValue;
    private double rate;
    private double result;
    private String message;
    private int status;

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getMonetaryValue() {
        return monetaryValue;
    }

    public void setMonetaryValue(double monetaryValue) {
        this.monetaryValue = monetaryValue;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
