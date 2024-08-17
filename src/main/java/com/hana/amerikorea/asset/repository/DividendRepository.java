package com.hana.amerikorea.asset.repository;

import com.hana.amerikorea.asset.domain.Dividend;
import com.hana.amerikorea.asset.domain.StockInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DividendRepository extends JpaRepository<Dividend,Long> {
    List<Dividend> findByStockInfoTickerSymbol(String tickerSymbol);
    Optional<Dividend> findByStockInfoAndDividendDate(StockInfo stockInfo, LocalDate date);

    @Query("SELECT d FROM Dividend d WHERE d.stockInfo.tickerSymbol = :tickerSymbol AND YEAR(d.dividendDate) = 2023")
    List<Dividend> findDividendsByTickerSymbolAndYear(String tickerSymbol);
}
