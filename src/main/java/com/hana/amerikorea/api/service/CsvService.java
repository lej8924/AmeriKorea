package com.hana.amerikorea.api.service;


import com.hana.amerikorea.api.model.StockData;
import com.hana.amerikorea.asset.domain.StockInfo;
import com.hana.amerikorea.asset.domain.type.Sector;
import com.hana.amerikorea.asset.repository.StockInfoRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private StockInfoRepository stockInfoRepository;

    @PostConstruct
    public void init() {
        loadCsvData(KRX, krxMap, true);
        loadCsvData(NASDAQ, nasdaqMap, false);
    }

/*    private void loadCsvData(String filePath, Map<String, StockData> stockMap, boolean isKorean) {
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
    }*/

    private void loadCsvData(String filePath, Map<String, StockData> stockMap, boolean isKorean) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();
            for (String[] record : records) {
                String symbol = record[0]; // 종목 코드
                String name = record[1]; // 종목명
                String sector = isKorean ? record[2] : record[3]; // 섹터
                String industry = isKorean ? record[3] : record[2]; // 산업

                Sector sectorEnum;
                try {
                    sectorEnum = Sector.valueOf(sector.toUpperCase());
                } catch (IllegalArgumentException e) {
                    sectorEnum = Sector.OTHER;
                }

                StockData stockData = new StockData(symbol, name, sectorEnum.name(), industry);

                // 이미 존재하는 ticker_symbol 확인
                if (stockInfoRepository.existsByTickerSymbol(symbol)) {
                    System.out.println("이미 존재하는 ticker_symbol: " + symbol);
                    continue; // 중복된 항목 건너뛰기
                }

                // 존재하지 않으면 데이터베이스에 삽입
                StockInfo stockInfo = new StockInfo(name, symbol, sectorEnum, industry);
                stockInfoRepository.save(stockInfo);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

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


