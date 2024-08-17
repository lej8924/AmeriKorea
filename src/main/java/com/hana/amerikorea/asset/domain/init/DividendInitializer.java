package com.hana.amerikorea.asset.domain.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hana.amerikorea.asset.domain.Dividend;
import com.hana.amerikorea.asset.domain.StockInfo;
import com.hana.amerikorea.asset.repository.DividendRepository;
import com.hana.amerikorea.asset.repository.StockInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Order(1)
public class DividendInitializer implements CommandLineRunner {

    private final StockInfoRepository stockInfoRepository;
    private final DividendRepository dividendRepository;

    @Autowired
    public DividendInitializer(StockInfoRepository stockInfoRepository, DividendRepository dividendRepository) {
        this.dividendRepository = dividendRepository;
        this.stockInfoRepository = stockInfoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String jsonFilePath = "src/main/resources/dividends_2023.json";
        ObjectMapper objectMapper = new ObjectMapper();
        List<StockInfo> allStockInfos = stockInfoRepository.findAll();

        // JSON 파일 로드
        Map<String, Map<String, Double>> dividendsData = objectMapper.readValue(new File(jsonFilePath), Map.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");

        // JSON 데이터 Iterate 및 저장
        dividendsData.forEach((tickerSymbol, dividends) -> {
            Optional<StockInfo> stockInfoOptional = stockInfoRepository.findByTickerSymbol(tickerSymbol);

            if (stockInfoOptional.isPresent()) {
                StockInfo stockInfo = stockInfoOptional.get();

                dividends.forEach((date, amount) -> {
                    LocalDate dividendDate = LocalDate.parse(date, formatter);
                    if(dividendDate.getYear()==2023) {
                        Dividend dividend = new Dividend();
                        dividend.setStockInfo(stockInfo);
                        dividend.setDividendDate(dividendDate);
                        dividend.setDividendAmount(amount);
                        dividendRepository.save(dividend);

                    }
                });
            } else {
                System.out.println("Dividend data has been loaded into the database.");
            }
        });
    }
}
