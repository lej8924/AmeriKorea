package com.hana.amerikorea.portfolio.domain.init;

import com.hana.amerikorea.portfolio.domain.Stock;
import com.hana.amerikorea.portfolio.domain.type.Sector;
import com.hana.amerikorea.portfolio.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;


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
        Stock stock1 = new Stock("AAPL", "Apple Inc.", Sector.IT, "Consumer Electronics", "NASDAQ", "USA", 50, 500000, 10000, 5000, 150, "March", 1.5);
        Stock stock2 = new Stock("GOOGL", "Alphabet Inc.", Sector.COMMUNICATION, "Internet Services", "NASDAQ", "USA", 30, 450000, 12000, 7000, 160, "June", 1.2);
        Stock stock3 = new Stock("AMZN", "Amazon.com Inc.", Sector.CD, "E-Commerce", "NASDAQ", "USA", 20, 300000, 15000, 8000, 170, "September", 1.0);
        Stock stock4 = new Stock("TSLA", "Tesla Inc.", Sector.CD, "Automobiles", "NASDAQ", "USA", 10, 200000, 20000, 10000, 180, "December", 0.8);
        Stock stock5 = new Stock("MSFT", "Microsoft Corporation", Sector.IT, "Software", "NASDAQ", "USA", 40, 400000, 9000, 6000, 155, "March", 1.3);
        Stock stock6 = new Stock("NVDA", "NVIDIA Corporation", Sector.IT, "Semiconductors", "NASDAQ", "USA", 25, 250000, 9500, 5000, 145, "April", 1.7);
        Stock stock7 = new Stock("NFLX", "Netflix Inc.", Sector.COMMUNICATION, "Entertainment", "NASDAQ", "USA", 15, 150000, 8000, 4000, 200, "May", 0.9);
        Stock stock8 = new Stock("FB", "Meta Platforms Inc.", Sector.COMMUNICATION, "Social Media", "NASDAQ", "USA", 35, 350000, 10500, 5000, 175, "July", 1.1);
        Stock stock9 = new Stock("BABA", "Alibaba Group", Sector.CD, "E-Commerce", "NYSE", "China", 40, 400000, 11000, 6000, 130, "August", 1.6);
        Stock stock10 = new Stock("ORCL", "Oracle Corporation", Sector.IT, "Software", "NYSE", "USA", 28, 280000, 9000, 4000, 135, "November", 1.4);
//        Stock stock11 = new Stock("INTC", "Intel Corporation", Sector.IT, "Semiconductors", "NASDAQ", "USA", 22, 220000, 7500, 3500, 125, "January", 1.8);
//        Stock stock12 = new Stock("CSCO", "Cisco Systems", Sector.IT, "Networking", "NASDAQ", "USA", 30, 300000, 8500, 4500, 140, "February", 1.9);
//        Stock stock13 = new Stock("PEP", "PepsiCo Inc.", Sector.CS, "Beverages", "NASDAQ", "USA", 18, 180000, 6500, 3000, 115, "April", 2.0);
//        Stock stock14 = new Stock("KO", "Coca-Cola Company", Sector.CS, "Beverages", "NYSE", "USA", 20, 200000, 7000, 3500, 120, "June", 2.1);
//        Stock stock15 = new Stock("WMT", "Walmart Inc.", Sector.CS, "Retail", "NYSE", "USA", 25, 250000, 8500, 4500, 130, "August", 1.5);
//        Stock stock16 = new Stock("MCD", "McDonald's Corporation", Sector.CD, "Restaurants", "NYSE", "USA", 12, 120000, 5500, 2500, 110, "September", 1.6);
//        Stock stock17 = new Stock("DIS", "Walt Disney Company", Sector.COMMUNICATION, "Entertainment", "NYSE", "USA", 28, 280000, 9500, 5000, 150, "October", 1.4);
//        Stock stock18 = new Stock("PYPL", "PayPal Holdings Inc.", Sector.IT, "Payments", "NASDAQ", "USA", 19, 190000, 7000, 3500, 125, "November", 1.7);
//        Stock stock19 = new Stock("V", "Visa Inc.", Sector.FINANCIAL, "Payments", "NYSE", "USA", 33, 330000, 10500, 5500, 170, "December", 1.2);
//        Stock stock20 = new Stock("JPM", "JPMorgan Chase & Co.", Sector.FINANCIAL, "Banking", "NYSE", "USA", 15, 150000, 8000, 4000, 135, "January", 1.9);

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
//        stockRepository.save(stock11);
//        stockRepository.save(stock12);
//        stockRepository.save(stock13);
//        stockRepository.save(stock14);
//        stockRepository.save(stock15);
//        stockRepository.save(stock16);
//        stockRepository.save(stock17);
//        stockRepository.save(stock18);
//        stockRepository.save(stock19);
//        stockRepository.save(stock20);
    }
}
