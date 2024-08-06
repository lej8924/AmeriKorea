package com.hana.amerikorea.portfolio.domain.init;

import com.hana.amerikorea.portfolio.domain.Stock;
import com.hana.amerikorea.portfolio.domain.type.DividendFrequency;
import com.hana.amerikorea.portfolio.domain.type.Sector;
import com.hana.amerikorea.portfolio.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StockInitializer implements CommandLineRunner {

    private final StockRepository stockRepository;

    @Autowired
    public StockInitializer(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 더미 데이터 삽입
        Stock stock1 = new Stock("AAPL", "Apple Inc.", Sector.IT, "Consumer Electronics", "NASDAQ", "USA", 50, 500000, 10000, 5000, 150, "March", 1.5, 0.82, DividendFrequency.QUARTERLY);
        Stock stock2 = new Stock("GOOGL", "Alphabet Inc.", Sector.COMMUNICATION, "Internet Services", "NASDAQ", "USA", 30, 450000, 12000, 7000, 160, "June", 1.2, 0.00, DividendFrequency.ANNUALLY);
        Stock stock3 = new Stock("AMZN", "Amazon.com Inc.", Sector.CD, "E-Commerce", "NASDAQ", "USA", 20, 300000, 15000, 8000, 170, "September", 1.0, 0.00, DividendFrequency.ANNUALLY);
        Stock stock4 = new Stock("TSLA", "Tesla Inc.", Sector.CD, "Automobiles", "NASDAQ", "USA", 10, 200000, 20000, 10000, 180, "December", 0.8, 0.00, DividendFrequency.ANNUALLY);
        Stock stock5 = new Stock("MSFT", "Microsoft Corporation", Sector.IT, "Software", "NASDAQ", "USA", 40, 400000, 9000, 6000, 155, "March", 1.3, 0.56, DividendFrequency.QUARTERLY);
        Stock stock6 = new Stock("NVDA", "NVIDIA Corporation", Sector.IT, "Semiconductors", "NASDAQ", "USA", 25, 250000, 9500, 5000, 145, "April", 1.7, 0.15, DividendFrequency.QUARTERLY);
        Stock stock7 = new Stock("NFLX", "Netflix Inc.", Sector.COMMUNICATION, "Entertainment", "NASDAQ", "USA", 15, 150000, 8000, 4000, 200, "May", 0.9, 0.00, DividendFrequency.ANNUALLY);
        Stock stock8 = new Stock("FB", "Meta Platforms Inc.", Sector.COMMUNICATION, "Social Media", "NASDAQ", "USA", 35, 350000, 10500, 5000, 175, "July", 1.1, 0.00, DividendFrequency.ANNUALLY);
        Stock stock9 = new Stock("BABA", "Alibaba Group", Sector.CD, "E-Commerce", "NYSE", "China", 40, 400000, 11000, 6000, 130, "August", 1.6, 0.00, DividendFrequency.ANNUALLY);
        Stock stock10 = new Stock("ORCL", "Oracle Corporation", Sector.IT, "Software", "NYSE", "USA", 28, 280000, 9000, 4000, 135, "November", 1.4, 0.32, DividendFrequency.QUARTERLY);

        stockRepository.save(stock1);
        stockRepository.save(stock2);
        stockRepository.save(stock3);
        stockRepository.save(stock4);
        stockRepository.save(stock5);
        stockRepository.save(stock6);
        stockRepository.save(stock7);
        stockRepository.save(stock8);
        stockRepository.save(stock9);
        stockRepository.save(stock10);
    }
}
