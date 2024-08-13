//package com.hana.amerikorea.api.service;
//
//
//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvException;
//import jakarta.annotation.PostConstruct;
//import org.springframework.stereotype.Service;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class CsvService {
//
//    private static final String KRX = "src/main/resources/csv/krx.csv";
//    private static final String NASDAQ = "src/main/resources/csv/nasdaq.csv";
//    private Map<String, String> krxMap = new HashMap<>();
//    private Map<String, String> nasdaqMap = new HashMap<>();
//
//    @PostConstruct
//    public void init() {
//        loadCsvData(KRX, krxMap);
//        loadCsvData(NASDAQ, nasdaqMap);
//    }
//
//    private void loadCsvData(String filePath, Map<String, String> stockMap) {
//        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
//            List<String[]> records = reader.readAll();
//            for (String[] record : records) {
//                String Symbol = record[0]; //종목코드
//                String Name = record[1]; //종목명
//                stockMap.put(Name, Symbol);
//            }
//        } catch (IOException | CsvException e) {
//            e.printStackTrace();
//        }
//    }
//    public String getStockCode(String stockName, boolean isKorean) {
//        Map<String, String > stockMap = isKorean?krxMap:nasdaqMap;
//        String lowerCaseStockName = stockName.toLowerCase();
//
//        for (Map.Entry<String, String>  entry:stockMap.entrySet()) {
//            if(entry.getKey().toLowerCase().contains(lowerCaseStockName)) {
//                return entry.getValue();
//            }
//        }
//        return "종목코드를 못 찾았습니다";
//    }
//}
//
//
