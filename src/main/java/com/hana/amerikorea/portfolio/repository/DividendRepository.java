package com.hana.amerikorea.portfolio.repository;

import com.hana.amerikorea.portfolio.domain.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DividendRepository extends JpaRepository<Dividend,Long> {
    List<Dividend> findByAssetTickerSymbol(String tickerSymbol);
}
