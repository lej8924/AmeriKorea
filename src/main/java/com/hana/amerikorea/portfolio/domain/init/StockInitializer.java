package com.hana.amerikorea.portfolio.domain.init;

import com.hana.amerikorea.portfolio.domain.Stock;
import com.hana.amerikorea.portfolio.domain.type.Sector;
import com.hana.amerikorea.portfolio.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StockInitializer implements CommandLineRunner {

    private final PortfolioRepository portfolioRepository;

    @Autowired
    public StockInitializer(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 더미 데이터 삽입
        Stock stock1 = new Stock("AAPL", "Apple Inc.", Sector.ENERGY, "Consumer Electronics", "NASDAQ", "USA");
        Stock stock2 = new Stock("GOOGL", "Alphabet Inc.", Sector.REAL_ESTATE, "Internet Services", "NASDAQ", "USA");
        Stock stock3 = new Stock("AMZN", "Amazon.com Inc.", Sector.ENERGY, "E-Commerce", "NASDAQ", "USA");
        Stock stock4 = new Stock("TSLA", "Tesla Inc.", Sector.IT, "Automobiles", "NASDAQ", "USA");
        Stock stock5 = new Stock("MSFT", "Microsoft Corporation", Sector.HEALTH, "Software", "NASDAQ", "USA");

        portfolioRepository.save(stock1);
        portfolioRepository.save(stock2);
        portfolioRepository.save(stock3);
        portfolioRepository.save(stock4);
        portfolioRepository.save(stock5);
    }
}
