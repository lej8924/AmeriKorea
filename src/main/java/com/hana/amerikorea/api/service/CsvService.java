package com.hana.amerikorea.api.service;


import com.hana.amerikorea.api.model.StockData;
import com.hana.amerikorea.portfolio.domain.type.Sector;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.annotation.PostConstruct;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvService {

    private static final String KRX = "src/main/resources/csv/krx.csv";
    private static final String NASDAQ = "src/main/resources/csv/nasdaq.csv";

    private Map<String, StockData> krxMap = new HashMap<>();
    private Map<String, StockData> nasdaqMap = new HashMap<>();

    @PostConstruct
    public void init() {
        loadCsvData(KRX, krxMap, true);
        loadCsvData(NASDAQ, nasdaqMap, false);
    }

    private void loadCsvData(String filePath, Map<String, StockData> stockMap, boolean isKorean) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();
            for (String[] record : records) {
                String symbol = record[0]; //종목코드
                String name = record[1]; //종목명
                String sector = isKorean ? record[2] : record[3]; //섹터
                String industry = isKorean ? record[3] : record[2]; //산업

                Sector sectorEnum;
                try {
                    sectorEnum = Sector.valueOf(sector.toUpperCase());
                } catch (IllegalArgumentException e) {
                    sectorEnum = Sector.OTHER;
                }

                StockData stockData = new StockData(symbol, name, sectorEnum.name(), industry);
                stockMap.put(name, stockData);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
/*    public String getStockCode(String stockName, boolean isKorean) {
        Map<String, String > stockMap = isKorean?krxMap:nasdaqMap;
        String lowerCaseStockName = stockName.toLowerCase();

        for (Map.Entry<String, String>  entry:stockMap.entrySet()) {
            if(entry.getKey().toLowerCase().contains(lowerCaseStockName)) {
                return entry.getValue();
            }
        }
        return "종목코드를 못 찾았습니다";
    }*/

    public StockData findStockData(String stockName, boolean isKorean) {
        Map<String, StockData> stockMap = isKorean ? krxMap:nasdaqMap;
        String lowerCaseStockName = stockName.toLowerCase();

        StockData stockData = stockMap.get(lowerCaseStockName);
        if(stockData!=null) {
            return stockData;
        }

        for(Map.Entry<String, StockData> entry : stockMap.entrySet()) {
            if(entry.getKey().toLowerCase().contains(lowerCaseStockName)) {
                return entry.getValue();
            }
        }

        return null;
    }

    public StockData getStockData(String stockName, boolean isKorean) {
        return findStockData(stockName, isKorean);
    }
}


