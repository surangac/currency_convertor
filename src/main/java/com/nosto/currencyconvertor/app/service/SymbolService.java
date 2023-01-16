package com.nosto.currencyconvertor.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Service
public class SymbolService {

    private final ExchangeService exchangeService;
    private final ObjectMapper objectMapper;
    private final String filePath;
    private Map<String, String> symbolMap = null;

    public SymbolService(
        ExchangeService exchangeService,
        @Value("${symbol-file}") String symbolFile,
        ObjectMapper objectMapper
    ) {
        this.exchangeService = exchangeService;
        this.filePath = symbolFile;
        this.objectMapper = objectMapper;
    }

    public boolean isValidSymbol(String symbolCode) {
        if(symbolMap == null) {
            loadSymbols();
        }

        return symbolMap.containsKey(symbolCode);
    }

    @PostConstruct
    private void loadSymbols() {
        try {
            String content = loadSymbolContent();
            if(content != null) {
                Map<String, Object> responseData = objectMapper.readValue(content, Map.class);
                symbolMap = (HashMap) responseData.get("symbols");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Cacheable(cacheNames = "symbols", condition = "#result != null and !#result.empty", cacheManager = "exchangeRatesCacheManager")
    private String loadSymbolContent() {
        File file = new File(filePath);
        String content = null;
        try {
            if(file.exists() && !file.isDirectory()) {
                content = new String(Files.readAllBytes(file.toPath()));

            } else {
                content = exchangeService.loadSymbols();
                writeToFile(content);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private void writeToFile(String jsonBody) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath);
            fileWriter.write(jsonBody);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
